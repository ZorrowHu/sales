package com.platform.sales.surface;

import com.platform.sales.entity.Record;
import com.platform.sales.repository.BrandRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandRecordServiceImpl implements BrandRecordService {
    @Autowired
    private BrandRecordRepository brandRecordRepository;

    @Override
    public Record create(Record record) {
        return brandRecordRepository.save(record);
    }

    @Override
    public List<Record> findByUserAndOp(Integer id) {
        return brandRecordRepository.findAllByUsersUserIdAndOpUserId(id,id);
    }

    @Override
    public List<Record> findByUser(Integer id) {
        return brandRecordRepository.findAllByUsersUserIdAndOpUserIdIsNot(id,id);
    }

    @Override
    public List<Record> findByOp(Integer id) {
        return brandRecordRepository.findAllByOpUserIdAndUsersUserIdNot(id, id);
    }
}
