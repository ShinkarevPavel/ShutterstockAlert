package com.nobody.configuration;

import com.nobody.entity.Header;
import com.nobody.entity.TelegramCredentials;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@ComponentScan("com.nobody")
public class HibernateConfiguration {

    @Bean
    public SessionFactory sessionFactory() throws URISyntaxException {
        return new org.hibernate.cfg.Configuration()
                .setProperties(hibernateProperties())
                .addAnnotatedClass(Header.class)
                .addAnnotatedClass(TelegramCredentials.class)
                .buildSessionFactory();
    }

    @Bean
    public Session session() throws URISyntaxException {
        return sessionFactory().openSession();
    }

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        dataSource.setJdbcUrl(getUrl());
        return dataSource;
    }


    private Properties hibernateProperties() throws URISyntaxException {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.connection.url", getUrl());
        return properties;
    }

    private String getUrl() throws URISyntaxException {
        final String dbName = "db3ddcsf3dfh7c";
        final String urlPrefix = "jdbc:postgresql://";
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
//        URI dbUri = new URI("postgres://prfaglnvxrezui:9995f601a4287a1c985de90a7fdaba95624a5270dd1d45554476f4b9f1ee31b3@ec2-52-3-2-245.compute-1.amazonaws.com:5432/db3ddcsf3dfh7c");
        String host =dbUri.getHost();
        int port =dbUri.getPort();
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        return urlPrefix +  host + ":" + port + "/" + dbName + "?sslmode=require&user=" + username + "&password=" + password;
    }

    private String getUsername() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
//        URI dbUri = new URI("postgres://prfaglnvxrezui:9995f601a4287a1c985de90a7fdaba95624a5270dd1d45554476f4b9f1ee31b3@ec2-52-3-2-245.compute-1.amazonaws.com:5432/db3ddcsf3dfh7c");
        return dbUri.getUserInfo().split(":")[0];
    }

    private String getPassword() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
//        URI dbUri = new URI("postgres://prfaglnvxrezui:9995f601a4287a1c985de90a7fdaba95624a5270dd1d45554476f4b9f1ee31b3@ec2-52-3-2-245.compute-1.amazonaws.com:5432/db3ddcsf3dfh7c");
        return dbUri.getUserInfo().split(":")[1];
    }
}
