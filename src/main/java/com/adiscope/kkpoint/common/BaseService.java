package com.adiscope.kkpoint.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public abstract class BaseService<Req,Res,Entity> implements CRUDInterface<Req,Res> {
    @Autowired(required = false)
    protected JpaRepository<Entity, Long> baseRepository;
}
