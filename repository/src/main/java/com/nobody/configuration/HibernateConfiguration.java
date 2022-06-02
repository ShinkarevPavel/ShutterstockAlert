package com.nobody.configuration;

import com.nobody.entity.Header;
import com.nobody.entity.TelegramCredentials;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import java.util.Properties;

@Configuration
@PropertySource("classpath:hibernate.properties")
@ComponentScan("com.nobody")
public class HibernateConfiguration {

//    @Value("${hibernate.connection.username}")
//    private String username;
//    @Value("${hibernate.connection.password}")
//    private String password;
//    @Value("${hibernate.connection.driver_class}")
//    private String db_driver;
//    @Value("${hibernate.connection.url}")
//    private String url;

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .setProperties(hibernateProperties())
                .addAnnotatedClass(Header.class)
                .addAnnotatedClass(TelegramCredentials.class)
                .buildSessionFactory();
    }

    @Bean
    public Session session() {
        return sessionFactory().openSession();
    }

    @Bean
    public DataSource dataSource() throws URISyntaxException {
//        URI dbUri = new URI("postgres://prfaglnvxrezui:9995f601a4287a1c985de90a7fdaba95624a5270dd1d45554476f4b9f1ee31b3@ec2-52-3-2-245.compute-1.amazonaws.com:5432/db3ddcsf3dfh7c");
        URI dbUri = new URI("DATASOURCE_URL");

        String host =dbUri.getHost();
        int port =dbUri.getPort();
        String dbName = "db3ddcsf3dfh7c";
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" +  host + ":" + port + "/" + dbName + "?sslmode=require&user=" + username + "&password=" + password;

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(dbUrl);
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }
}
