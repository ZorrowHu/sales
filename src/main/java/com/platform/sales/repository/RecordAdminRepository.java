package com.platform.sales.repository;

import com.platform.sales.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordAdminRepository extends JpaRepository<Record, Integer> {
    public Record findByStatus( String status);
}
