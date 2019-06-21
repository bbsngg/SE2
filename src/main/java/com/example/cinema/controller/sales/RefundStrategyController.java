package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.RefundStrategyService;
import com.example.cinema.vo.RefundStrategyForm;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "退票策略api")
@RestController
@RequestMapping("/refund")
public class RefundStrategyController {
    @Autowired
    RefundStrategyService refundStrategyService;

    /**
     * 管理员增加一种退票策略
     * @param refundStrategyForm
     * @return
     */
    @ApiOperation(value = "增加退票策略")
    @ApiImplicitParam(name = "refundStrategyForm",value = "退票策略Form",dataType = "RefundStrategyForm")
    @PostMapping("/add")
    public ResponseVO publishRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm){
        return refundStrategyService.publishRefundStrategy(refundStrategyForm);
    }

    /**
     * 管理员更新一种退票策略
     * @param refundStrategyForm
     * @return
     */
    @ApiOperation(value = "更新退票策略")
    @ApiImplicitParam(name = "refundStrategyForm",value = "退票策略Form",dataType = "RefundStrategyForm")
    @PostMapping("/update")
    public ResponseVO updateRefundStrategy(@RequestBody RefundStrategyForm refundStrategyForm){
        return refundStrategyService.updateRefundStrategy(refundStrategyForm);
    }

    /**
     * 通过id搜索一种策略
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id搜索退票策略")
    @ApiImplicitParam(name = "id",value = "退票策略id",dataType = "Integer")
    @GetMapping("{id}")
    public ResponseVO searchOneStrategyById(@PathVariable int id){
        return refundStrategyService.getRefundStrategyById(id);
    }

    /**
     * 获取所有策略
     * @return
     */
    @ApiOperation(value = "获取所有退票策略")
    @GetMapping("/all")
    public ResponseVO searchAllStrategy(){
        return refundStrategyService.getAllRefundStrategy();
    }

    /**
     * 通过id删除一种策略
     * @param refundStrategyForm
     * @return
     */
    @ApiOperation(value = "根据id删除退票策略")
    @ApiImplicitParam(name = "refundStrategyForm",value = "退票策略Form",dataType = "RefundStrategyForm")
    @DeleteMapping("/delete")
    public ResponseVO deleteOneStrategyById(@RequestBody RefundStrategyForm refundStrategyForm){
        return refundStrategyService.deleteRefundStrategyById(refundStrategyForm.getId());
    }
}
