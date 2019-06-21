package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.logs.TransactionLogServiceForBl;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.activity.ActivityServiceForBl;
import com.example.cinema.blImpl.promotion.coupon.CouponServiceForBl;
import com.example.cinema.blImpl.promotion.vip.VIPServiceForBl;
import com.example.cinema.blImpl.user.BankAccountServiceForBl;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.promotion.Coupon;
import com.example.cinema.po.Hall;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.po.Ticket;
import com.example.cinema.po.*;
import com.example.cinema.po.promotion.Activity;
import com.example.cinema.po.promotion.VIPCard;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;

    @Autowired
    CouponServiceForBl couponService;
    @Autowired
    ActivityServiceForBl activityService;
    @Autowired
    VIPServiceForBl vipService;
    @Autowired
    TransactionLogServiceForBl transService;
    @Autowired
    BankAccountServiceForBl bankAccountService;
    @Autowired
    RefundStrategyForBl refundStrategyService;


    /**
     * 锁座【增加票但状态为未付款】
     * @Date 05/09
     * @param ticketForm
     * @return
     * 确定座位
     */
    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try{
            int scheduleId = ticketForm.getScheduleId();
            int userId = ticketForm.getUserId();
            int movieId = scheduleService.getScheduleItemById(scheduleId).getMovieId();
    
            TicketWithCouponVO ticketWithCouponVO = new TicketWithCouponVO();
    
            List<TicketVO> ticketVOS = new ArrayList<>();
            List<SeatForm> orderSeats = ticketForm.getSeats();//获取下单的座位
            List<Activity> activities = activityService.selectActivitiesByMovie(movieId);
    
            for (SeatForm orderSeat : orderSeats) {
                Ticket ticket = new Ticket();
//                ticket.setId(); Id实现自增长，无需在此设置（参见TicketMapper.xml文件）
                ticket.setUserId(userId);
                ticket.setScheduleId(scheduleId);
                ticket.setColumnIndex(orderSeat.getColumnIndex());
                ticket.setRowIndex(orderSeat.getRowIndex());
                ticket.setState(0); // 未付款
                //存入ticket
                ticketMapper.insertTicket(ticket);
                Ticket temp=ticketMapper.selectTicketByScheduleIdAndSeat(scheduleId,orderSeat.getColumnIndex(),orderSeat.getRowIndex());
                ticketVOS.add(temp.getVO());
            }
            double total = orderSeats.size() * scheduleService.getScheduleItemById(scheduleId).getFare();
            ticketWithCouponVO.setTicketVOList(ticketVOS);
            ticketWithCouponVO.setTotal(total);
            ticketWithCouponVO.setActivities(activities);
            List<Coupon> coupons = new ArrayList<>();
            couponService.selectCouponByUser(userId).forEach(coupon -> { // 对每一个优惠券，检查是否对应该电影
                for (Activity activity: activities){
                    if (activity.getCoupon().getId()==coupon.getId()
                            && coupon.getTargetAmount()<=total){
                        coupons.add(coupon);
                        break;
                    }
                };
            });
            ticketWithCouponVO.setCoupons(coupons);
            return ResponseVO.buildSuccess(ticketWithCouponVO);
        } catch (Exception e){
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    /**
     * 完成购票【不使用会员卡】流程包括 校验优惠券 和 根据优惠活动赠送优惠券
     * @Date
     * @param id (ticketId)
     * @param couponId
     * @return
     */
    @Override
    @Transactional
    //没有优惠券的couponId==0
    public ResponseVO completeTicket(List<Integer> id, int couponId,int cardId) {
        return complete(id,couponId,cardId);
    }

    /**
     * 获取排片信息（包括座位）
     * @param scheduleId
     * @return
     */
    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);//获得本场电影的所有电影票
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);//获得排片信息
            Hall hall=hallService.getHallById(schedule.getHallId());//获得影厅信息
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {//遍历ticket列表，将已购买的座位设置为1
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    
    /**
     * 获得用户买过的票   **test**
     * @Date 05/10
     * @param userId
     * @return
     */
    @Override
    public ResponseVO getTicketByUser(int userId) {
        List<Ticket> tickets = ticketMapper.selectTicketByUser(userId);
        List<TicketWithScheduleVO> ticketWithScheduleVOS = new ArrayList<>();
        for (Ticket ticket: tickets) {
            TicketWithScheduleVO tempTick = new TicketWithScheduleVO();
            ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticket.getScheduleId());
            tempTick = ticket.getWithScheduleVO(scheduleItem);
            ticketWithScheduleVOS.add(tempTick);
        }
        return ResponseVO.buildSuccess(ticketWithScheduleVOS);
    
    }
    
    /**
     * 完成购票【使用会员卡】流程包括会员卡扣费、校验优惠券和根据优惠活动赠送优惠券
     * @Date 05/10
     * @param id
     * @param couponId
     * @return
     */
    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        return complete(id,couponId,-1);
    }
    private ResponseVO complete(List<Integer> id, int couponId, int cardId){
        Ticket ticket=ticketMapper.selectTicketById(id.get(0));//获取其中一张电影票
        ScheduleItem scheduleItem=scheduleService.getScheduleItemById(ticket.getScheduleId());
        int userId=ticket.getUserId();
        double total=id.size() * (scheduleItem.getFare());;//计算支付总金额
        double balance=9999;
    
        if (couponId!=0) { //使用优惠券
            Coupon useCoupon=null;//判断优惠券是否存在
            List<Coupon> userCoupons=couponService.selectCouponByUser(userId);//获取用户持有的优惠券
            for(Coupon coupon:userCoupons){
                if(coupon.getId()==couponId)
                    useCoupon=coupon;
            }
            /* 情况二：用户未持有相关优惠券*/
            if(useCoupon==null)
                return ResponseVO.buildFailure("获取用户优惠券失败");
            /* 情况一：用户使用相关优惠券*/
            //检查用户是否拥有订单相关的优惠券
            // 存在就从表里面删除，表示已经使用了
            if (couponService.deleteCouponUser(couponId, userId).equals("失败"))
                return ResponseVO.buildFailure("删除用户优惠券失败");
            total=total-useCoupon.getDiscountAmount();
            //修改订单状态
        }
        else{ //不使用优惠券
            /* 情况三：用户不使用优惠券，先记录消费记录与订票状态，再赠送优惠券*/
            Coupon tempCoupon=null;//返回的优惠券
            double temp=0;//暂存最大值
            List<Activity> activities=activityService.selectActivitiesByMovie(scheduleItem.getMovieId());
            if (activities==null)
                return ResponseVO.buildFailure("获取活动失败");
            for(Activity activity:activities){
                if(total>=activity.getCoupon().getTargetAmount()){
                    if(activity.getCoupon().getTargetAmount()>temp){
                        temp=activity.getCoupon().getTargetAmount();
                        tempCoupon=activity.getCoupon();
                    }
                }
            }
            //有相关活动
            if(tempCoupon!=null) {
                if (couponService.insertCouponUser(tempCoupon.getId(), userId).equals("失败"))
                    //赠送优惠券
                    return ResponseVO.buildFailure("增加用户优惠券失败");//本来有优惠券需要增加但是失败
            }
            else{
                System.out.println("用户未获得满足要求的优惠券");//可能是因为使用过优惠券的情况
            }
        }
        //修改订单状态
        for(Integer ticketId:id)
            ticketMapper.updateTicketState(ticketId,1);//购买后修改state为1
        //新增一条消费记录
        String uses="";
        for(int i=0;i<id.size();i++){
            uses+=id.get(i)+((i!=id.size()-1)?",":"");
        }
        String res="";
        if(cardId==-1){
            VIPCard vipCard = vipService.getCardByUserIdForBl(userId); // 获取用户的VIP卡
            if (vipCard == null) return ResponseVO.buildFailure("会员卡不存在");
            balance = vipCard.getBalance(); // 获取VIP卡内余额
            if(total>balance)
                return ResponseVO.buildFailure("余额不足");
            vipService.deduct(vipCard.getId(),total);
        }
        else{
            BankCard bankCard=bankAccountService.getBankCardById(cardId);
            res=bankCard.getAccountNumber()+"";
            if(bankCard==null) return ResponseVO.buildFailure("银行卡不存在");
            balance=bankCard.getBalance();
            if(total>balance)
                return ResponseVO.buildFailure("余额不足");
            bankAccountService.deduct(cardId,total);
            //获取银行卡的余额
        }
        TransactionLog tran = new TransactionLog();
        tran.setAmount(total);
        tran.setUserId(userId);
        tran.setUses(uses);
        tran.setMethod(((cardId==-1)?"vip":res));//此处应该传银行卡号,需要修改接口
        transService.addTran(tran);
    
        return ResponseVO.buildSuccess("成功");
    }

    /**
     * 取消锁座（只有状态是"锁定中"的可以取消）  **test**
     * @Date 05/10
     * @param id
     * @return
     */
    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        for (int ticketId: id ){
            if (ticketMapper.selectTicketById(ticketId).getState()==0){
                try{
                    ticketMapper.deleteTicket(ticketId);
                }catch(Exception e){
                    e.printStackTrace();
                    return ResponseVO.buildFailure("删除ticket失败");
                }
            }
            else
                return ResponseVO.buildFailure("此座位未被锁定");
        }
        return ResponseVO.buildSuccess();
    }

    /**
     * 用户（一张）退票：查询transaction_log表退钱
     * @param ticketId
     * @return
     */
    @Override
    public ResponseVO refundTicket(int ticketId) {

        List<TransactionLog> transactionLogList = transService.getAllTrans();
        double amount = 0;
        String tickets = "";
        String method = "";//"vip"-会员卡，其他（实际上是int银行卡id）-银行卡
        int userId = 0;

        //查询transaction_log表找到消费记录
        for(TransactionLog transactionLog : transactionLogList){
            if(transactionLog.getUses().contains(ticketId+"")){
                tickets = transactionLog.getUses();
                amount = transactionLog.getAmount();
                method = transactionLog.getMethod();
                userId = transactionLog.getUserId();
                break;
            }
        }
        if(tickets.equals(""))
            return ResponseVO.buildFailure("该交易不存在");

        //选择退票策略
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间

        double percent = selectRefStrategy(tickets); //获取退票的策略的折算额度
        if (percent==0) return ResponseVO.buildFailure("此票已过期");
        double refundAmount = amount / tickets.split(",").length; //单张票价
        refundAmount *= percent; //根据退票策略

        //根据method决定退钱到银行卡还是会员卡

        try{
            if(method.equals("vip")) { //退到会员卡
                VIPCard vipCard = vipService.getCardByUserIdForBl(userId);
                String ret = vipService.deduct(vipCard.getId(), -refundAmount);
                if (!ret.equals("成功")) throw new Exception(ret);
            }
            else{
                BankCard bankCard = bankAccountService.getBankCardByAccountName(Integer.parseInt(method));//method实际上是银行卡id
                int bankCardId=bankCard.getId();
                bankAccountService.deduct(bankCardId,-refundAmount);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("更新会员卡余额失败:" + e.getMessage());
        }

        //查询ticket表退票
        if (ticketMapper.selectTicketById(ticketId).getState()==1){
            try{
                int ret = ticketMapper.deleteTicket(ticketId);
                if (ret==0) throw new Exception("票不存在");
            }catch (Exception e){
                e.printStackTrace();
                return ResponseVO.buildFailure("删除ticket失败:" + e.getMessage());
            }
        }
        else
            return ResponseVO.buildFailure("退票失败:暂未付款");
        //优惠券不退

        return ResponseVO.buildSuccess("退票成功");
    }
    //获取退票策略
    public double selectRefStrategy(String tickets) { //时间
        int ticketid = Integer.valueOf(tickets.split(",")[0]);
        int scheId = ticketMapper.selectTicketById(ticketid).getScheduleId();
        ScheduleItem scheduleItem = scheduleService.getScheduleItemById(scheId);

        Date startTime = scheduleItem.getStartTime();
        Date nowTime = new Date();
        long count = startTime.getTime() - nowTime.getTime();
        long time = (count) / (1000*60*60);

        return refundStrategyService.getRefStratPercentByTime(time);
    }


    @Override
    public ResponseVO getDetailsByTicketId(String idList) {
        try{
            List<Seat> seats=new ArrayList<>();
            String[] array=idList.split(",");
            for(String id:array){
                Ticket ticket=ticketMapper.selectTicketById(Integer.parseInt(id));
                Seat seat=new Seat(ticket.getRowIndex()+1,ticket.getColumnIndex()+1);
                seats.add(seat);
            }
            ScheduleItem s=scheduleService.getScheduleItemById(ticketMapper.selectTicketById(Integer.parseInt(array[0])).getScheduleId());
            TicketDetailsVO ticketDetailsVO=new TicketDetailsVO(seats,s.getMovieName(),s.getStartTime(),s.getEndTime());
            return ResponseVO.buildSuccess(ticketDetailsVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure(e.getMessage());
        }

    }

}
