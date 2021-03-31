package com.platform.sales.surface;

import com.platform.sales.entity.Record;
import com.platform.sales.entity.Users;
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
        return brandRecordRepository.findAllByUsersUserIdAndOpUserIdNot(id,id);
    }

    @Override
    public List<Record> findByOp(Integer id) {
        return brandRecordRepository.findAllByOpUserIdAndUsersUserIdNot(id, id);
    }

    @Override
    public List<Record> findByUser(Users user) {
        return brandRecordRepository.findAllByUsersAndOpNot(user,user);
    }

    @Override
    public List<Record> findByOp(Users user) {
        return brandRecordRepository.findAllByOpAndUsersNot(user,user);
    }

    @Override
    public void delByUserOrOp(Integer id_1, Integer id_2) {
        brandRecordRepository.deleteAllByUsersUserIdOrOpUserId(id_1, id_2);
    }

    @Override
    public List<Record> findAllByUser_UserId(Integer id) {
        return brandRecordRepository.findAllByUsers_UserId(id);
    }

    @Override
    public Record update(Record record) {
        return brandRecordRepository.save(record);
    }

    @Override
    public List<Record> findAllByUser_UserIdOrOp_UserId(Integer id1, Integer id2) {
        return brandRecordRepository.findAllByUsers_UserIdOrOp_UserId(id1,id2);
    }

}
