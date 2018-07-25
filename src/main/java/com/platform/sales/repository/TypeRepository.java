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

        //original SQL statement should follow the dataset name("type")
        @Query( value = "SELECT DISTINCT content1 FROM type ", nativeQuery = true)
        List<String> getPrimary();

        //HQL statement should follow the project entity name("Type")
        @Query( value = "SELECT DISTINCT content2 FROM Type WHERE content1 = ?1")
        List<String> getSecondary(String secondary);
}
