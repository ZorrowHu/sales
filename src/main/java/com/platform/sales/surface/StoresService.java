package com.platform.sales.surface;
import java.util.List;
import java.util.*;
import com.platform.sales.entity.Stores;
public interface StoresService {

    List<Stores> getAllstores();
    Stores getStoreByID(Integer store_id);
    Stores addStoreInfo(Stores stores);
    void deleteByStoreID(Integer store_id);
    Stores updateStudentInfo(Stores student);
    List<Stores> findAllByUser_UserId(Integer id);
}
