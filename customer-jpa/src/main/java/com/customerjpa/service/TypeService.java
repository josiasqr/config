package com.customerjpa.service;


import com.customerjpa.entity.Type;

import java.util.List;

public interface TypeService {
    void init();
    List<Type> create(Type type);
    List<Type> listTypes();
}
