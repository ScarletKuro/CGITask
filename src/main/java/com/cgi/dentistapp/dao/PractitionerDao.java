package com.cgi.dentistapp.dao;

import com.cgi.dentistapp.dao.entity.PractitionerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

@Repository
public class PractitionerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(PractitionerEntity practitioner) {
        this.entityManager.persist(practitioner);
    }

    public List<PractitionerEntity> getAllPractitioners() {
        return this.entityManager.createQuery("SELECT e FROM PractitionerEntity e").getResultList();
    }

    public List<PractitionerEntity> getAllPractitionersById(List<Long> ids) {
        Query query = this.entityManager.createQuery("SELECT e FROM PractitionerEntity e WHERE e.id IN (:id)");
        query.setParameter("id", ids);

        return query.getResultList();
    }

    public PractitionerEntity getById(Long id){
        return this.entityManager.find(PractitionerEntity.class, id);
    }
}
