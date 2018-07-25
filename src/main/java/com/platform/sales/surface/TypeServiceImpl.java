package com.platform.sales.surface;

import com.platform.sales.entity.Type;
import com.platform.sales.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public Type findById(Integer id) {
        return typeRepository.findById(id).get();
    }

    @Override
    public Type addType(Type type) {
            return typeRepository.save(type);
    }

    @Override
    public void deleteById(Integer id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type updateType(Type type) {
        return typeRepository.save(type);
    }
}
