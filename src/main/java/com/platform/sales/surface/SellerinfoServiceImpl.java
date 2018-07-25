package com.platform.sales.surface;

import com.platform.sales.entity.SellerInfo;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.SellerinfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SellerinfoServiceImpl implements SellerinfoService {
    @Autowired
    private SellerinfoRepository sellerinfoRepository;
    @Override
    public SellerInfo updateSeller(SellerInfo seller_info) {
        return sellerinfoRepository.save(seller_info);
    }
    public List<SellerInfo> findById(Integer id){
        return sellerinfoRepository.findSellerInfosByUser_UserId(id);
    }
    public List<Users> getUser(Integer id){return sellerinfoRepository.getUser(id);}
}
