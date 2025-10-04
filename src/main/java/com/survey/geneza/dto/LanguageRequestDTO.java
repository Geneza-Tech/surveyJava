package com.survey.geneza.dto;

public class LanguageRequestDTO {

    private String state;
    private String district;
    private String block;
    private String village;
    private String languageName;
    private Integer personId; // send personId instead of Person object

    // Getters & Setters
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getLanguageName() { return languageName; }
    public void setLanguageName(String languageName) { this.languageName = languageName; }

    public Integer getPersonId() { return personId; }
    public void setPersonId(Integer personId) { this.personId = personId; }
}

