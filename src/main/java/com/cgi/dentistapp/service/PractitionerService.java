package com.cgi.dentistapp.service;

import com.cgi.dentistapp.dao.PractitionerDao;
import com.cgi.dentistapp.dao.entity.PractitionerEntity;
import com.cgi.dentistapp.dto.PractionerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PractitionerService {

    @Autowired
    private PractitionerDao practitionerVisitDao;

    public void addPractitioner(String practitionerName) {
        PractitionerEntity practitioner = new PractitionerEntity(practitionerName);
        this.practitionerVisitDao.create(practitioner);
    }

    public List<PractitionerEntity> listPractitioners() {
        return this.practitionerVisitDao.getAllPractitioners();
    }

    public List<PractitionerEntity> listPractitionersById(List<Long> ids) {
        return this.practitionerVisitDao.getAllPractitionersById(ids);
    }

    public List<PractionerDTO> getCheckedPractioners(List<PractitionerEntity> practitioners){
        List<PractionerDTO> allPractitioners = new ArrayList<>();
        for(PractitionerEntity practitionerEntity : this.listPractitioners()){
            Boolean selected = practitioners.contains(practitionerEntity);
            allPractitioners.add(new PractionerDTO(practitionerEntity.getId(), practitionerEntity.getPractitionerName(), selected));
        }
        return allPractitioners;
    }


    public PractitionerEntity getById(Long id){
        return this.practitionerVisitDao.getById(id);
    }
}
