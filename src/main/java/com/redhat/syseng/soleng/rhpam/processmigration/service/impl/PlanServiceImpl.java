package com.redhat.syseng.soleng.rhpam.processmigration.service.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.redhat.syseng.soleng.rhpam.processmigration.model.Plan;
import com.redhat.syseng.soleng.rhpam.processmigration.service.PlanService;

@ApplicationScoped
public class PlanServiceImpl implements PlanService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Plan get(Long id) {
        TypedQuery<Plan> query = entityManager.createNamedQuery("Plan.findById", Plan.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Plan> findAll() {
        return entityManager.createNamedQuery("Plan.findAll", Plan.class).getResultList();
    }

    @Override
    @Transactional
    public Plan delete(Long id) {
        Plan plan = entityManager.find(Plan.class, id);
        entityManager.remove(plan);
        return plan;
    }

    @Override
    @Transactional
    public Plan save(Plan plan) {
        entityManager.persist(plan);
        return plan;
    }

}
