package com.platform.sales.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import com.platform.sales.entity.Stores;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoresRepository extends JpaRepository<Stores,Integer> {

}
