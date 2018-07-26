package com.platform.sales.repository;

import com.platform.sales.entity.Record;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRecordRepository extends JpaRepository<Record,Integer> {

    List<Record> findAllByUsersUserIdAndOpUserIdNot(Integer id_1, Integer id_2);

    List<Record> findAllByOpUserIdAndUsersUserIdNot(Integer id_1, Integer id_2);

    List<Record> findAllByUsersUserIdAndOpUserId(Integer id_1, Integer id_2);

    List<Record> findAllByUsersAndOpNot(Users user, Users op);

    List<Record> findAllByOpAndUsersNot(Users op, Users user);
}
