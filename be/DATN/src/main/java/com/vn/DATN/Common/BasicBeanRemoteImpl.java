package com.vn.DATN.Common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicBeanRemoteImpl implements BasicBeanRemote {
    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public <EntityT> EntityT create(EntityT entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public <EntityT> EntityT edit(EntityT entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public void remove(Object entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public <EntityT> EntityT find(Class<EntityT> classEntity, Object id) {
        return getEntityManager().find(classEntity, id);
    }

    @Override
    public <EntityT> List<EntityT> findAll(Class<EntityT> classEntity) {
        CriteriaQuery<EntityT> cq = getEntityManager().getCriteriaBuilder().createQuery(classEntity);
        cq.select(cq.from(classEntity));
        return getEntityManager().createQuery(cq).getResultList();
    }


}
