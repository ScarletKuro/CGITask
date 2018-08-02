package com.cgi.dentistapp.dto;

public class PractionerDTO {

    private Long id;

    private String practitionerName;

    private Boolean selected;

    public PractionerDTO(){

    }



    public PractionerDTO(Long id, String practitionerName, boolean selected){
        this.setId(id);
        this.setPractitionerName(practitionerName);
        this.setSelected(selected);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPractitionerName() { return this.practitionerName; }

    public void setPractitionerName(String practitionerName) { this.practitionerName = practitionerName; }

    public void setSelected(Boolean selected) { this.selected = selected; }

    public Boolean getSelected() { return this.selected; }

    @Override
    public String toString(){
        return this.practitionerName;
    }
}
