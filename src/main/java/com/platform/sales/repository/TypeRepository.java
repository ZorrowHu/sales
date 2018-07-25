package com.platform.sales.repository;

import com.platform.sales.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Integer> {

        @Override
        Page<Type> findAll(Pageable pageable);

        @Query( value = "SELECT DISTINCT content_1 FROM type", nativeQuery = true)
        List<String> getPrimary();

        @Query( value = "SELECT DISTINCT content_2 FROM type WHERE content_1 = ?1")
        List<String> getSecondary(String secondary);
}
