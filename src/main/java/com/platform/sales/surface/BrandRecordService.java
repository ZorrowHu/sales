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

    List<Record> findAllByUser_UserId(Integer id);
}
