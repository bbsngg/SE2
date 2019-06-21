package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPTypeService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.promotion.VIPTypeForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: VIPTypeController
 * @description: 管理会员卡种类信息
 * @author: bbsngg
 * @create: 2019-06-14 19:51
 */
@Api(description = "会员卡类型api")
@RestController()
@RequestMapping("/viptype")
public class VIPTypeController {

    @Autowired
    VIPTypeService vipTypeService;

    /**
     * 会员卡策略相关操作（会员卡管理）
     * 1. /viptype/addType
     * 2. /viptype/updateType
     * 3. /viptype/getAllType
     * 4. /viptype/delTypeById
     * 5. /viptype/delTypeById
     */

    /**
     * 获取所有会员卡策略
     * @return
     */
    @ApiOperation(value = "获取所有会员卡策略")
    @GetMapping("/getAllType")
    public ResponseVO getAllType(){
        return vipTypeService.getAllType();
    }

    /**
     * 通过策略id获取该种会员卡信息
     * @param typeId
     * @return
     */
    @ApiOperation(value = "根据id获取会员卡策略")
    @ApiImplicitParam(name = "typeId",value = "会员卡类型id",dataType = "Integer")
    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(@RequestParam int typeId){ return vipTypeService.getVIPInfo(typeId);}

    /**
     * 增加一种会员卡（策略）
     * @param vipTypeForm
     * @return
     */
    @ApiOperation(value = "增加一种会员卡")
    @ApiImplicitParam(name = "vipForm",value = "会员卡类型Form",dataType = "VIPTypeForm")
    @PostMapping("/addType")
    public ResponseVO addType(@RequestBody VIPTypeForm vipTypeForm){
        return vipTypeService.addType(vipTypeForm);
    }

    /**
     * 修改会员卡策略
     * @param vipTypeForm
     * @return
     */
    @ApiOperation(value = "更新会员卡策略")
    @ApiImplicitParam(name = "vipTypeForm",value = "会员卡类型Form",dataType = "VIPTypeForm")
    @PostMapping("/updateType")
    public ResponseVO updateType(@RequestBody VIPTypeForm vipTypeForm){
        return vipTypeService.updateType(vipTypeForm);
    }

    /**
     * 通过策略id删除该会员卡策略
     * @param typeId
     * @return
     */
    @ApiOperation(value = "根据id删除会员卡策略")
    @ApiImplicitParam(name = "typeId",value = "会员卡类型id",dataType = "Integer")
    @PostMapping("/delTypeById")
    public ResponseVO delTypeById(@RequestParam int typeId){
        return vipTypeService.delTypeById(typeId);
    }

}
