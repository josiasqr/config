package com.customerjpa.service.impl;

import com.customerjpa.entity.Type;
import com.customerjpa.repository.TypeRepository;
import com.customerjpa.service.TypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService{
    private static final Logger logger = LogManager.getLogger(TypeServiceImpl.class);
    @Autowired
    private TypeRepository typeRepository;

    @Override
    @PostConstruct
    public void init() {
        if(listTypes().isEmpty()){
            Type per = new Type();
            per.setDescription("Personal");
            create(per);

            Type emp = new Type();
            emp.setDescription("Empresarial");
            create(emp);

            logger.info("Se crearon los tipos de Customer 'Personal' y 'Empresarial'");
        }
    }

    @Override
    public List<Type> create(Type type) {
        return List.of(typeRepository.save(type));
    }

    @Override
    public List<Type> listTypes() {
        return typeRepository.findAll();
    }
}
