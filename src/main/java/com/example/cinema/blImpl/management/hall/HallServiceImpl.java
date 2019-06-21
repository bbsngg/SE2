package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallBatchDeleteForm;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("查找影厅失败");
        }
    }

    @Override
    public ResponseVO addHall(HallForm hallForm) {
        try {
            hallMapper.insertOneHall(hallForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("增加影厅失败");
        }
    }

    @Override
    public ResponseVO updateHall(HallForm hallForm){
        try{
            hallMapper.updateOneHall(hallForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("更新影厅失败");
        }
    }

    @Override
    public ResponseVO deleteBatchOfHall(HallBatchDeleteForm hallBatchDeleteForm){
        try{
            List<Integer> hallIdList=hallBatchDeleteForm.getHallIdList();
            if(hallIdList.size()==0){
                return ResponseVO.buildFailure("id列表不可为空");
            }
            hallMapper.deleteHallBatch(hallIdList);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("删除影厅失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 查询所有影厅信息
     *
     * @return List<Hall>
     */
    @Override
    public List<Hall> selectAllHall() {
        try {
            return hallMapper.selectAllHall();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }
}
