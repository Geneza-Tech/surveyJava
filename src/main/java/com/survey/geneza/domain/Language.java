package com.survey.geneza.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@Entity
@Table(name = "Language")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Language implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String state;
    private String district;
    private String block;
    private String village;
    private String languageName;
 @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personId", nullable = false)  // FK column in the table
        @JsonIgnore
    private Person person;

    @Transient
    private Integer personId;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

     public Integer getPersonId() { return personId; }
    public void setPersonId(Integer personId) { this.personId = personId; }
}
