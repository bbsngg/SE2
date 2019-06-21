package com.example.cinema.controller.management;

import com.example.cinema.bl.statistics.MovieLikeService;
import com.example.cinema.vo.MovieBatchOffForm;
import com.example.cinema.vo.MovieForm;
import com.example.cinema.bl.management.MovieService;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**电影管理
 * @author fjj
 * @date 2019/3/12 6:17 PM
 */
@Api(description = "电影管理api")
@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieLikeService movieLikeService;

    /**
     * 增加电影
     * @param addMovieForm
     * @return
     */
    @ApiOperation(value = "增加电影")
    @ApiImplicitParam(name = "addMovieForm",value = "电影Form",dataType = "MovieForm")
    @RequestMapping(value = "/movie/add", method = RequestMethod.POST)
    public ResponseVO addMovie(@RequestBody MovieForm addMovieForm){
        return movieService.addMovie(addMovieForm);
    }

    /**
     * 搜索电影
     * @param id
     * @param userId
     * @return
     */
    @ApiOperation(value = "搜索电影")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "电影id",dataType = "Integer"),
            @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    })
    @RequestMapping(value = "/movie/{id}/{userId}", method = RequestMethod.GET)
    public ResponseVO searchOneMovieByIdAndUserId(@PathVariable int id, @PathVariable int userId){
        return movieService.searchOneMovieByIdAndUserId(id, userId);
    }

    /**
     * 查找所有电影
     * @return
     */
    @ApiOperation(value = "查找所有电影")
    @RequestMapping(value = "/movie/all", method = RequestMethod.GET)
    public ResponseVO searchAllMovie(){
        //返回结果中包括已经下架的电影
        return movieService.searchAllMovie();
    }

    /**
     * 查找正在上映的电影
     * @return
     */
    @ApiOperation(value = "查找正在上映的电影")
    @RequestMapping(value = "/movie/all/exclude/off", method = RequestMethod.GET)
    public ResponseVO searchOtherMoviesExcludeOff(){
        //返回结果中不包括已经下架的电影
        return movieService.searchOtherMoviesExcludeOff();
    }

    /**
     * 标记电影想看
     * @param movieId
     * @param userId
     * @return
     */
    @ApiOperation(value = "标记电影想看")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "movieId",value = "电影id",dataType = "Integer"),
            @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    })
    @RequestMapping(value = "/movie/{movieId}/like", method = RequestMethod.POST)
    public ResponseVO likeMovie(@PathVariable int movieId,@RequestParam int userId){
        return movieLikeService.likeMovie(userId,movieId);
    }

    /**
     * 取消想看的标记
     * @param movieId
     * @param userId
     * @return
     */
    @ApiOperation(value = "取消想看电影的标记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "movieId",value = "电影id",dataType = "Integer"),
            @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    })
    @RequestMapping(value = "/movie/{movieId}/unlike", method = RequestMethod.POST)
    public ResponseVO unlikeMovie(@PathVariable int movieId,@RequestParam int userId){
        return movieLikeService.unLikeMovie(userId,movieId);
    }

    /**
     * 获得某电影想看人数
     * @param movieId
     * @return
     */
    @ApiOperation(value = "获得某电影想看人数")
    @ApiImplicitParam(name = "movieId",value = "电影id",dataType = "Integer")
    @RequestMapping(value = "/movie/{movieId}/like/count", method = RequestMethod.GET)
    public ResponseVO getMovieLikeCounts(@PathVariable int movieId){
        return movieLikeService.getCountOfLikes(movieId);
    }

    /**
     * 获取当日某电影想看人数
     * @param movieId
     * @return
     */
    @ApiOperation(value = "获取当日某电影想看人数")
    @ApiImplicitParam(name = "movieId",value = "电影id",dataType = "Integer")
    @RequestMapping(value = "/movie/{movieId}/like/date", method = RequestMethod.GET)
    public ResponseVO getMovieLikeCountByDate(@PathVariable int movieId){
        return movieLikeService.getLikeNumsGroupByDate(movieId);
    }

    /**
     * 根据关键字查找电影
     * @param keyword
     * @return
     */
    @ApiOperation(value = "根据关键字查找电影")
    @ApiImplicitParam(name = "keyword",value = "关键字",dataType = "String")
    @RequestMapping(value = "/movie/search",method = RequestMethod.GET)
    public ResponseVO getMovieByKeyword(@RequestParam String keyword){
        return movieService.getMovieByKeyword(keyword);
    }

    /**
     * 批量下架电影
     * @param movieBatchOffForm
     * @return
     */
    @ApiOperation(value = "批量下架电影")
    @ApiImplicitParam(name = "movieBatchOffForm",value = "电影id的list",dataType = "MovieBatchOffForm")
    @RequestMapping(value = "/movie/off/batch",method = RequestMethod.POST)
    public ResponseVO pullOfBatchOfMovie(@RequestBody MovieBatchOffForm movieBatchOffForm){
        return movieService.pullOfBatchOfMovie(movieBatchOffForm);
    }

    /**
     * 更新电影信息
     * @param updateMovieForm
     * @return
     */
    @ApiOperation(value = "更新电影信息")
    @ApiImplicitParam(name = "updateMovieForm",value = "电影Form",dataType = "MovieForm")
    @RequestMapping(value = "/movie/update",method = RequestMethod.POST)
    public ResponseVO updateMovie(@RequestBody MovieForm updateMovieForm){
        return movieService.updateMovie(updateMovieForm);
    }
}
