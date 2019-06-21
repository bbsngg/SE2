package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallBatchDeleteForm;
import com.example.cinema.vo.HallForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 查询所有影厅信息
     * @return
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * 插入一条hall信息
     * @param hallForm
     * @return
     */
    int insertOneHall(HallForm hallForm);

    /**
     * 更新hall信息
     * @param hallForm
     * @return
     */
    int updateOneHall(HallForm hallForm);

    /**
     * 删除一条hall信息
     * @param hallId
     * @return
     */
    int deleteOneHall(int hallId);

    /**
     * 根据hall的list删除hall信息
     * @param list
     * @return
     */
    int deleteHallBatch(List<Integer> list);
}
