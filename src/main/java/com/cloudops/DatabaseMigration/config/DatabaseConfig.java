package com.cloudops.DatabaseMigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.universitydatabase")
    public DataSource universityDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean (name = "entityManagerFactory")
    @Primary
    public EntityManagerFactory publicEntityManagerFactory(){
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(dataSource());
        managerFactoryBean.setPackagesToScan("com.cloudops.DatabaseMigration.dao.public_entity");
        managerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        managerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        managerFactoryBean.afterPropertiesSet();
        return managerFactoryBean.getObject();
    }

    @Bean
    public EntityManagerFactory universityEntityManagerFactory(){
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(universityDataSource());
        managerFactoryBean.setPackagesToScan("com.cloudops.DatabaseMigration.dao.university_entity;");
        managerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        managerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        managerFactoryBean.afterPropertiesSet();
        return managerFactoryBean.getObject();
    }

//    @Bean
//    //@Primary
//    public JpaTransactionManager publicTransactionManager(){
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setDataSource(dataSource());
//        transactionManager.setEntityManagerFactory(publicEntityManagerFactory());
//        return transactionManager;
//    }

    @Bean
    @Primary
    public JpaTransactionManager uniTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(universityDataSource());
        transactionManager.setEntityManagerFactory(universityEntityManagerFactory());
        return transactionManager;
    }
}
