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

        //Get the typeId by categories for relative goods searching
        Type findTypeByContent1AndAndContent2AndContent3(String primary, String secondary, String tertiary);

        //original SQL statement should follow the dataset name("type")
        @Query( value = "SELECT DISTINCT content1 FROM type ", nativeQuery = true)
        List<String> getPrimary();

        //HQL statement should follow the project entity name("Type")
        @Query( value = "SELECT DISTINCT content2 FROM Type WHERE content1 = ?1")
        List<String> getSecondary(String secondary);

        @Query( value = "SELECT DISTINCT content3 FROM Type WHERE content1 = ?1 AND content2 = ?2")
        List<String> getTertiary(String primary, String secondary);

}
