package com.coderscampus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "proffessoEntityManagerFactory", transactionManagerRef = "proffessoTransactionManager", basePackages = {
        "com.coderscampus.proffesso"})
@Profile("local")
public class ProffessoDbConfig {
    Logger log = LoggerFactory.getLogger(PrimaryDbConfig.class);

    @Value("${PROFFESSO_DB_USERNAME}")
    private String dbUsername;
    @Value("${PROFFESSO_DB_PASSWORD}")
    private String dbPassword;

    @Bean(name = "proffessoDataSource")
    public DataSource getDataSource() {

        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/proffessoproddb")
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }

    @Bean(name = "proffessoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean proffessoEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("proffessoDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(dataSource)
                .packages("com.coderscampus.proffesso")
                .persistenceUnit("proffesso")
                .build();
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();

        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show-sql", "true");
        properties.put("hibernate.implicit_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        properties.put("hibernate.physical_naming_strategy",
                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        em.setJpaVendorAdapter(adapter);
        em.getJpaPropertyMap().putAll(properties);
        return em;
    }

    @Bean(name = "proffessoTransactionManager")
    public PlatformTransactionManager proffessoTransactionManager(
            @Qualifier("proffessoEntityManagerFactory") EntityManagerFactory proffessoEntityManagerFactory) {
        return new JpaTransactionManager(proffessoEntityManagerFactory);
    }
}
