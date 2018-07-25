package com.platform.sales.surface;
import com.platform.sales.entity.Stores;
import com.platform.sales.repository.StoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public  class StoresServiceImpl implements StoresService {
    @Autowired
    private StoresRepository storesRepository;


    @Override
    public List<Stores> getAllstores() {
        return storesRepository.findAll();
    }

    @Override
    public Stores getStoreByID(Integer store_id) {
        return storesRepository.findById(store_id).get();
    }

    @Override
    public Stores addStoreInfo(Stores stores) {
        return storesRepository.save(stores);
    }

    @Override
    public void deleteByStoreID(Integer store_id) {
        storesRepository.deleteById(store_id);
    }

    @Override
    public Stores updateStudentInfo(Stores stores) {
        return storesRepository.save(stores);
    }
}
