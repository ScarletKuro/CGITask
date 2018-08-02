package com.cgi.dentistapp.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import com.cgi.dentistapp.dao.entity.PractitionerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgi.dentistapp.dao.DentistVisitDao;
import com.cgi.dentistapp.dao.entity.DentistVisitEntity;

@Service
@Transactional
public class DentistVisitService {

    @Autowired
    private DentistVisitDao dentistVisitDao;

    public void addVisit(String dentistName, Date visitTime, List<PractitionerEntity> practitioners) {
        DentistVisitEntity visit = new DentistVisitEntity(dentistName, visitTime, practitioners);
        this.dentistVisitDao.create(visit);
    }

    public Boolean checkDate(Date visitDate) { return this.dentistVisitDao.checkDate(visitDate); }

    public void updateVisit(DentistVisitEntity visit){
        this.dentistVisitDao.update(visit);
    }

    public List<DentistVisitEntity> searchByName(String dentistName) {return this.dentistVisitDao.searchByName(dentistName); }

    public List<DentistVisitEntity> listVisits() {
        return this.dentistVisitDao.getAllVisits();
    }

    public void removeById(Long id){
        this.dentistVisitDao.removeById(id);
    }

    public DentistVisitEntity getById(Long id) { return this.dentistVisitDao.getById(id); }


}
