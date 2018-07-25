package com.platform.sales.repository;

import com.platform.sales.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRecordRepository extends JpaRepository<Record,Integer> {

    List<Record> findAllByUsersUserIdAndOpUserId(Integer id_1, Integer id_2);
}
