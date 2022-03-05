package com.coderscampus;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "proffessoEntityManagerFactory", transactionManagerRef = "proffessoTransactionManager", basePackages = {
        "com.coderscampus.proffesso" })
public class ProffessoDbConfig {
    Logger log = LoggerFactory.getLogger(PrimaryDbConfig.class);

    @Value("${PROFFESSO_DB_USERNAME}")
    private String dbUsername;
    @Value("${PROFFESSO_DB_PASSWORD}")
    private String dbPassword;

    @Bean(name = "proffessoDataSource")
    public DataSource getDataSource() {
        String ip = "mysqldb";
        try {
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> responseEntity = rt.getForEntity(
                    "http://169.254.169.254/latest/meta-data/public-ipv4",
                    String.class);
            ip = responseEntity.getBody() + ":3306";

        } catch (Exception e) {
            log.warn("falling back to default mysql connection string");
        }
        log.info("Assignment Submission Primary external ip: " + ip);
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://" + ip + "/proffessoproddb")
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
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show-sql", "true");
        em.setJpaProperties(properties);
        return em;
    }

    @Bean(name = "proffessoTransactionManager")
    public PlatformTransactionManager proffessoTransactionManager(
            @Qualifier("proffessoEntityManagerFactory") EntityManagerFactory proffessoEntityManagerFactory) {
        return new JpaTransactionManager(proffessoEntityManagerFactory);
    }
}
