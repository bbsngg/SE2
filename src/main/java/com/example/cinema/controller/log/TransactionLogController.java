package com.example.cinema.controller.log;

import com.example.cinema.bl.logs.TransactionLogService;
import com.example.cinema.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: TransactionLog
 * @description: 用户消费记录管理
 * @author: bbsngg
 * @create: 2019-06-15 12:37
 */
@Api(description = "消费记录api")
@RestController
@RequestMapping("/trans")
public class TransactionLogController {

    @Autowired
    TransactionLogService transactionLogService;


    /**
     * 获取所有的消费记录
     * @return
     */
    @ApiOperation(value = "获取所有的消费记录")
    @GetMapping("/getTrans")
    public ResponseVO getTransLog(){
        return transactionLogService.getTransLog();
    }

    /**
     * 获取用户的所有的消费记录
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取指定用户所有的消费记录")
    @ApiImplicitParam(name = "userId",value = "账号id",dataType = "Integer")
    @GetMapping("/getByUserId/{userId}")
    public ResponseVO getByUserId(@PathVariable int userId){
        return transactionLogService.getByUserId(userId);
    }

}
