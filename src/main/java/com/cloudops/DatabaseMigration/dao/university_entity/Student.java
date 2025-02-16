package com.cloudops.DatabaseMigration.dao.university_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {

    @Id
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private Long dept_id;
    private Boolean is_active;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getFirst_name() {return first_name;}
    public void setFirst_name(String first_name) {this.first_name = first_name;}

    public String getLast_name() {return last_name;}
    public void setLast_name(String last_name) {this.last_name = last_name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Long getDept_id() {return dept_id;}
    public void setDept_id(Long dept_id) {this.dept_id = dept_id;}

    public Boolean getIs_active() {return is_active;}
    public void setIs_active(Boolean is_active) {this.is_active = is_active;}
}
