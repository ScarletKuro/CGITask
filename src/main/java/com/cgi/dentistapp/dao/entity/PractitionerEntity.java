package com.cgi.dentistapp.dao.entity;

import org.aspectj.apache.bcel.generic.RET;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "practitioner")
public class PractitionerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "practitioner_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "practitioner_name", nullable = false)
    private String practitionerName;

    public PractitionerEntity(){

    }

    public PractitionerEntity(String practitionerName){
        this.setPractitionerName(practitionerName);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPractitionerName() { return this.practitionerName; }

    public void setPractitionerName(String practitionerName) { this.practitionerName = practitionerName; }

    @Override
    public String toString(){
        return this.practitionerName;
    }
}
