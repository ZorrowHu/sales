package com.platform.sales.repository;

import com.platform.sales.entity.BrandInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandInfoRepository extends JpaRepository<BrandInfo,Integer> {
    //根据users.userId查找品牌商信息
    BrandInfo findByUsersUserId(Integer id);

    //根据userId删除记录
    void deleteBrandInfoByUsersUserId(Integer id);

}
