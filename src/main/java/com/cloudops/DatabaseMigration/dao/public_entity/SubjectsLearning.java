package com.cloudops.DatabaseMigration.dao.public_entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subjects_learning")
public class SubjectsLearning {
    @Id
    private String id;
    private String sub_name;
    private String student_id;
    private String marks_obtained;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public String getSub_name() {return sub_name;}
    public void setSub_name(String sub_name) {this.sub_name = sub_name;}

    public String getStudent_id() {return student_id;}
    public void setStudent_id(String student_id) {this.student_id = student_id;}

    public String getMarks_obtained() {return marks_obtained;}
    public void setMarks_obtained(String marks_obtained) {this.marks_obtained = marks_obtained;}
}
