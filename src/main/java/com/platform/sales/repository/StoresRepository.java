package com.platform.sales.repository;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import com.platform.sales.entity.Stores;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface StoresRepository extends JpaRepository<Stores,Integer> {
    List<Stores> findAllByUser_UserId(Integer id);
    // 根据用户删除所有店铺
    @Transactional
    void deleteAllByUser(Users user);
}
