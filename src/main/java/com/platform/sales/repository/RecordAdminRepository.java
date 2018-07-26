package com.platform.sales.repository;

import com.platform.sales.entity.Record;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface RecordAdminRepository extends JpaRepository<Record, Integer> {
     List<Record> findAllByStatus(String status);


     @Transactional
     void deleteAllByUsersOrOp(Users user1, Users user2);
}
