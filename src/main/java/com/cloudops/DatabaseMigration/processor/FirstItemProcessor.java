package com.cloudops.DatabaseMigration.processor;

import com.cloudops.DatabaseMigration.dao.public_entity.Student;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstItemProcessor implements ItemProcessor<Student, com.cloudops.DatabaseMigration.dao.university_entity.Student> {

    @Override
    public com.cloudops.DatabaseMigration.dao.university_entity.Student process(Student item) throws Exception {

        System.out.println("Processed Id: "+item.getId());
        System.out.println("Email Id: "+item.getEmail());
        com.cloudops.DatabaseMigration.dao.university_entity.Student student = new com.cloudops.DatabaseMigration.dao.university_entity.Student();
        student.setId(item.getId()); // Ensure consistency
        student.setFirst_name(item.getFirst_name());
        student.setLast_name(item.getLast_name());
        student.setEmail(item.getEmail());
        student.setDept_id(item.getDept_id());
        student.setIs_active(item.getIs_active() != null ? Boolean.valueOf(item.getIs_active()) : false);
        return student;
    }
}
