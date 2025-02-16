package com.cloudops.DatabaseMigration.config;

import com.cloudops.DatabaseMigration.dao.public_entity.Student;
import com.cloudops.DatabaseMigration.processor.FirstItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class SampleJob {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    FirstItemProcessor firstItemProcessor;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("universityDataSource")
    private DataSource universityDataSource;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManagerFactory publicEntityManagerFactory;

    @Autowired
    @Qualifier("universityEntityManagerFactory")
    private EntityManagerFactory universityEntityManagerFactory;

//    @Autowired
//    @Qualifier("publicTransactionManager")
//    private JpaTransactionManager publicTransactionManager;

    @Autowired
    @Qualifier("uniTransactionManager")
    private JpaTransactionManager uniTransactionManager;

    @Bean
    public Job firstJob(){
        return new JobBuilder("databaseMigrationJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstStep())
                .build();
    }

    private Step firstStep(){
        return new StepBuilder("First chunk step",jobRepository)
                .<Student, com.cloudops.DatabaseMigration.dao.university_entity.Student>chunk(3,platformTransactionManager)
                .reader(jpaCursorItemReader())
                .processor(firstItemProcessor)
                .writer(jpaItemWriter())
                .faultTolerant()
                .skip(Throwable.class)
                .skipLimit(100)
                .retryLimit(3)
                .retry(Throwable.class)
                .transactionManager(uniTransactionManager)
                .build();
    }

    public JpaCursorItemReader<Student> jpaCursorItemReader(){
        JpaCursorItemReader<Student> jpaItemReader = new JpaCursorItemReader<>();
        jpaItemReader.setEntityManagerFactory(publicEntityManagerFactory);
        jpaItemReader.setQueryString("SELECT s FROM Student s");
        jpaItemReader.setSaveState(true);  // Save state to ensure progress is remembered
        //jpaItemReader.set
        return jpaItemReader;
    }

    public JpaItemWriter<com.cloudops.DatabaseMigration.dao.university_entity.Student> jpaItemWriter(){
        JpaItemWriter<com.cloudops.DatabaseMigration.dao.university_entity.Student> jpaWriter = new JpaItemWriter<>();
        jpaWriter.setEntityManagerFactory(universityEntityManagerFactory);
        return jpaWriter;
    }
}
