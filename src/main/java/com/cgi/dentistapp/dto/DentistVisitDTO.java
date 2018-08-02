package com.cgi.dentistapp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class DentistVisitDTO {

    @Size(min = 1, max = 50)
    String dentistName;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    Date visitDate;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    Date visitTime;

    @Size(min = 1)
    List<Long> practitioners;

    public DentistVisitDTO() {
    }

    public DentistVisitDTO(String dentistName, Date visitDate, Date visitTime) {
        this.setDentistName(dentistName);
        this.setVisitDate(visitDate);
        this.setVisitTime(visitTime);
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public Date getVisitDate() {
        return this.visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Date getVisitTime() {
        return this.visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public List<Long> getPractitioners() {
        return this.practitioners;
    }

    public void setPractitioners(List<Long> practitioners) {
        this.practitioners = practitioners;
    }
}
