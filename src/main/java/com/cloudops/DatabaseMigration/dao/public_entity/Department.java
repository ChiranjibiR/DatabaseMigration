package com.cloudops.DatabaseMigration.dao.public_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
    @Id
    private Long id;
    private String dept_name;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getDept_name() {return dept_name;}
    public void setDept_name(String dept_name) {this.dept_name = dept_name;}
}
