package com.platform.sales.surface;

import com.platform.sales.entity.Type;

import java.util.List;

public interface TypeService {
    // 查询所有类型
    List<Type> getAllTypes();
    // 根据id查询类型
    Type findById(Integer id);
    // 增加类型
    Type addType(Type type);
    // 根据id删除类型
    void deleteById(Integer id);
    // 修改类型
    Type updateType(Type type);
}
