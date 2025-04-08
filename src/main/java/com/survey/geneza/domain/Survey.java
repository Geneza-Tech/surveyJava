package com.survey.geneza.domain;
import java.io.Serializable;
import java.lang.StringBuilder;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Date;
import java.math.*;
import javax.xml.bind.annotation.*;
import javax.persistence.*;

/**
 * Survey Entity
 * 
 * Generated by Dunamis App Generator
 */

@Entity
@Table(name = "Survey")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "SurveyRest/com/survey/geneza/domain", name = "Survey")
public class Survey implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "id")
    @Basic(fetch = FetchType.EAGER)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement
    private Integer id;  
    @Column(name = "survey")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    private String survey;

    @Column(name = "survey_type")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    private String surveyType;
    


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

        
    public String getSurvey() {
        return survey;
    }
    
    public void setSurvey(String survey) {
        this.survey = survey;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }



    public Survey() {
    }


    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("id=[").append(id).append("] ");
        return buffer.toString();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((id == null) ? 0 : id.hashCode()));
        return result;
    }


    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Survey))
            return false;
        Survey equalCheck = (Survey) obj;
        if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
            return false;
        if (id != null && !id.equals(equalCheck.id))
            return false;
        return true;
    }

}