package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallBatchDeleteForm;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@Api(description = "影厅管理api")
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    /**
     * 获取所有影厅信息
     * @return
     */
    @ApiOperation(value = "获取所有影厅信息")
    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    /**
     * 增加影厅
     * @param hallForm
     * @return
     */
    @ApiOperation(value = "增加影厅")
    @ApiImplicitParam(name = "hallForm",value = "影厅Form",dataType = "HallForm")
    @RequestMapping(value = "hall/add", method = RequestMethod.POST)
    public ResponseVO addHall(@RequestBody HallForm hallForm){
        return hallService.addHall(hallForm);
    }

    /**
     * 更新影厅
     * @param hallForm
     * @return
     */
    @ApiOperation(value = "更新影厅")
    @ApiImplicitParam(name = "hallForm",value = "影厅Form",dataType = "HallForm")
    @RequestMapping(value = "hall/update", method = RequestMethod.POST)
    public ResponseVO updateHall(@RequestBody HallForm hallForm){
        return hallService.updateHall(hallForm);
    }

    /**
     * 删除影厅
     * @param hallBatchDeleteForm
     * @return
     */
    @ApiOperation(value = "删除影厅")
    @ApiImplicitParam(name = "hallBatchDeleteForm",value = "影厅id的list",dataType = "HallBatchDeleteForm")
    @RequestMapping(value = "hall/delete", method = RequestMethod.DELETE)
    public ResponseVO deleteBatchOfHall(@RequestBody HallBatchDeleteForm hallBatchDeleteForm){
        return hallService.deleteBatchOfHall(hallBatchDeleteForm);
    }
}
