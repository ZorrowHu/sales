package com.platform.sales.surface;

import com.platform.sales.entity.Record;
import com.platform.sales.entity.Users;

import java.util.List;

public interface BrandRecordService {
    Record create(Record record);

    List<Record> findByUserAndOp(Integer id);

    List<Record> findByUser(Integer id);

    List<Record> findByOp(Integer id);

    List<Record> findByUser(Users user);

    List<Record> findByOp(Users user);

    void delByUserOrOp(Integer id_1, Integer id_2);

    List<Record> findAllByUser_UserId(Integer id);
    //保存流水记录
    Record update(Record record);
    public List<Record> findAllByUser_UserIdOrOp_UserId(Integer id1,Integer id2);
}
