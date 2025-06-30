package com.vn.DATN.Common;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BasicBeanRemote {
    <EntityT> EntityT create(EntityT entity);

    <EntityT> EntityT edit(EntityT entity);

    void remove(Object entity);

    <EntityT> EntityT find(Class<EntityT> classEntity, Object id);

    <EntityT> List<EntityT> findAll(Class<EntityT> classEntity);

}
