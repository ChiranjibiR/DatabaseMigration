package com.cloudops.DatabaseMigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSourceProperties postgresDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource postgresDataSource(){
        return postgresDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(postgresDataSource());
        managerFactoryBean.setPackagesToScan("com.cloudops.DatabaseMigration.dao.public_entity");
        managerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        managerFactoryBean.setPersistenceUnitName("postgres");
        return managerFactoryBean;
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.university")
    public DataSourceProperties  universityDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource universityDataSource() {
        return universityDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "universityEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean universityEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(universityDataSource());
        factoryBean.setPackagesToScan("com.cloudops.DatabaseMigration.dao.university_entity");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPersistenceUnitName("university");
        return factoryBean;
    }

    @Bean
    @Primary
    public JpaTransactionManager postgresTransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory postgresEntityManagerFactory) {
        return new JpaTransactionManager(postgresEntityManagerFactory);
    }

    @Bean
    public JpaTransactionManager universityTransactionManager(@Qualifier("universityEntityManagerFactory") EntityManagerFactory universityEntityManagerFactory) {
        return new JpaTransactionManager(universityEntityManagerFactory);
    }
}
