package com.example.cinema.blImpl.statistics;

import com.example.cinema.bl.statistics.StatisticsService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.MovieServiceForBl;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.MovieMapper;
import com.example.cinema.data.statistics.StatisticsMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Autowired
    HallServiceForBl hallService;
    @Autowired
    MovieServiceForBl movieService;

    /**
     * 获取当天排片
     * @param date
     * @return List<MovieScheduleTimeVO>
     */

    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 获得总票房
     * @return List<MovieTotalBoxOfficeVO>
     */
    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    /**
     * 上座率
     * 8.1 StatisticsServiceImpl文件中的getMoviePlacingRateByDate方法
     * （此方法中运用到的相应的操作数据库的接口和实现请自行定义和实现，
     * 相应的结果需要自己定义一个VO类返回给前端）
     * @param date
     * @return
     */
    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) {
        //要求见接口说明
        try{
            List<Movie> movies=movieService.selectAllMovie();
            //最终返回的content内容
            List<MovieWithPlacingRateVO> movieWithPlacingRateVOS=new ArrayList<MovieWithPlacingRateVO>();
            //获取总座位数
            List<Hall> halls=hallService.selectAllHall();
            Map<String,Integer> m = new HashMap<String, Integer>();//构建一个电影及当日观看人数的键值对
            for(Movie movie:movies){
                m.put(movie.getName(),0);//初始化
            }
            //Map(key=电影名,value=当日观看人数)
            Calendar calendar=new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            Date nextDate = calendar.getTime();
            //TODO:需要实现XML
            List<MovieAudienceNumber> movieAudienceNumbers=statisticsMapper.selectAudienceNumber(date,nextDate);

            //初始化为0的数据
            for(Movie movie:movies){
                boolean hasMovie=false;
                for(MovieAudienceNumber movieAudienceNumber:movieAudienceNumbers){
                    if(movie.getName().equals(movieAudienceNumber.getName())){
                        hasMovie=true;
                        break;
                    }
                }
                if(!hasMovie){
                    MovieAudienceNumber movieAudienceNumber=new MovieAudienceNumber();
                    movieAudienceNumber.setMovieId(movie.getId());
                    movieAudienceNumber.setName(movie.getName());
                    movieAudienceNumber.setNumber(0);
                    movieAudienceNumbers.add(movieAudienceNumber);
                }
            }
            //给m赋值
            for(Hall hall:halls){
                //TODO:需要实现XML
                List<MovieScheduleTimeWithHall> movieScheduleTimeWithHalls=statisticsMapper.selectMovieScheduleTimeWithHall(hall.getId(),date,nextDate);
                for(MovieScheduleTimeWithHall movieScheduleTimeWithHall:movieScheduleTimeWithHalls){
                    if(hall.getId()==movieScheduleTimeWithHall.getHallId()){//如果是该影厅的电影就修改map值
                        String movieName=movieScheduleTimeWithHall.getName();//电影名
                        int temp=m.get(movieName);
                        m.put(movieName,temp+movieScheduleTimeWithHall.getTime()*hall.getRow()*hall.getColumn());
                    }
                }
            }
            //计算上座率
            for(MovieAudienceNumber movieAudienceNumber:movieAudienceNumbers){
                MovieWithPlacingRateVO movieWithPlacingRateVO=new MovieWithPlacingRateVO();
                String movieName=movieAudienceNumber.getName();
                movieWithPlacingRateVO.setName(movieName);
                movieWithPlacingRateVO.setPlacingRate((m.get(movieName)==0)?0.0:1.0*movieAudienceNumber.getNumber()/m.get(movieName));//×1.0表示转化为浮点数
                movieWithPlacingRateVOS.add(movieWithPlacingRateVO);
            }
            return ResponseVO.buildSuccess(movieWithPlacingRateVOS);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 受欢迎电影：
     * 7.1 StatisticsServiceImpl文件中的getPopularMovies方法
     * （此方法中运用到的相应的操作数据库的接口和实现请自行定义和实现，
     * 相应的结果需要自己定义一个VO类返回给前端）
     * @param days
     * @param movieNum
     * @return
     */
    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        //要求见接口说明
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date prevDate = getNumDayAfterDate(date, -days);
            List<MovieTotalBoxOffice> movieTotalBoxOffices=statisticsMapper.selectMovieTotalBoxOfficeByDate(prevDate,date);
            List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOS=new ArrayList<>();
            for(MovieTotalBoxOffice movieTotalBoxOffice:movieTotalBoxOffices){
                if(movieTotalBoxOffice.getBoxOffice()!=null) {//left join带来的null问题,票房为0的数据不需要被返回
                    MovieTotalBoxOfficeVO movieTotalBoxOfficeVO = new MovieTotalBoxOfficeVO(movieTotalBoxOffice);
                    movieTotalBoxOfficeVOS.add(movieTotalBoxOfficeVO);
                }
            }
            return ResponseVO.buildSuccess(movieTotalBoxOffices.subList(0,(movieNum>movieTotalBoxOffices.size())?movieTotalBoxOffices.size():movieNum));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    /**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }



    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }

    /**
     * po转为vo便于返回给前端
     * @param movieTotalBoxOfficeList
     * @return
     */
    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }
}
