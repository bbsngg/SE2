package com.example.cinema.bl.management;

import com.example.cinema.vo.HallBatchDeleteForm;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();

    /**
     * 增加影厅
     * @param hallForm
     * @return
     */
    ResponseVO addHall(HallForm hallForm);

    /**
     * 更新影厅
     * @param hallForm
     * @return
     */
    ResponseVO updateHall(HallForm hallForm);

    /**
     * 删除影厅
     * @param hallBatchDeleteForm
     * @return
     */
    ResponseVO deleteBatchOfHall(HallBatchDeleteForm hallBatchDeleteForm);
}
