package com.survey.geneza.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "SurveyRest/com/survey/geneza/domain", name = "User")
public class User {
    
    @Column(name = "id")
    @Id
    @Basic(fetch = FetchType.EAGER)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //@JsonIgnore
    @NotEmpty
    @Column(unique = true, nullable = false, name ="login")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    private String login;
    
    //@JsonIgnore
    @NotEmpty
    @Column(name ="password")
    @Basic(fetch = FetchType.EAGER)
    @XmlElement
    private String password;

    @ManyToOne
    @JoinColumn(name="personId")
    private Person person;
    
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roles = new HashSet<Role>();

    public User() {
    }

    public User(User user) {
        super();
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.person = user.getPerson();
        this.roles = user.getRoles();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}