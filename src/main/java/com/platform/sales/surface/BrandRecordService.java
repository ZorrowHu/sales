package com.platform.sales.surface;

import com.platform.sales.entity.Record;

import java.util.List;

public interface BrandRecordService {
    Record create(Record record);

    List<Record> findByUserAndOp(Integer id);
}