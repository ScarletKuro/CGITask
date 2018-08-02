package com.cgi.dentistapp.dao.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.*;

@Entity
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dentist_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "dentist_name", nullable = false)
    private String dentistName;

    @Column(name = "visit_time", nullable = false, unique = true)
    private Date visitDate;

    @ManyToMany
    @JoinTable(name = "dentist_practitioner_association", joinColumns = {
            @JoinColumn(name = "dentist_id")},
            inverseJoinColumns = {@JoinColumn(name = "practitioner_id")})
    private List<PractitionerEntity> practitioners;

    public DentistVisitEntity() {
    }

    public DentistVisitEntity(String dentistName, Date visitDate, List<PractitionerEntity> practitioners)
    {
        this.setDentistName(dentistName);
        this.setVisitDate(visitDate);
        this.setPractitioners(practitioners);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getVisitDate() {
        return this.visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getDentistName() { return this.dentistName; }

    public void setDentistName(String dentistName) { this.dentistName = dentistName; }

    public void setPractitioners(List<PractitionerEntity> practitioners) {
        this.practitioners = practitioners;
    }

    public List<PractitionerEntity> getPractitioners() { return this.practitioners; }

}
