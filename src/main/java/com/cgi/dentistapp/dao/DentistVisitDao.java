package com.cgi.dentistapp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

import java.util.Date;
import java.util.List;

@Repository
public class DentistVisitDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(DentistVisitEntity visit) {
        this.entityManager.persist(visit);
    }

    public void update(DentistVisitEntity visit){
        this.entityManager.merge(visit);
    }

    public List<DentistVisitEntity> searchByName(String dentistName){
        Query query = this.entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE e.dentistName LIKE (:dentistName)");
        query.setParameter("dentistName", "%" + dentistName + "%");
        return query.getResultList();
    }

    public Boolean checkDate(Date visitDate){
        Query query = this.entityManager.createQuery("SELECT e FROM DentistVisitEntity e WHERE e.visitDate = (:visitDate)");
        query.setParameter("visitDate", visitDate);
        return query.getResultList().size() == 0;
    }

    public List<DentistVisitEntity> getAllVisits() {
        return this.entityManager.createQuery("SELECT e FROM DentistVisitEntity e").getResultList();
    }

    public DentistVisitEntity getById(Long id){
        return this.entityManager.find(DentistVisitEntity.class, id);
    }

    public void removeById(Long id){
        this.entityManager.createQuery("DELETE FROM DentistVisitEntity e WHERE e.id =:id").setParameter("id", id).executeUpdate();;
    }
}
