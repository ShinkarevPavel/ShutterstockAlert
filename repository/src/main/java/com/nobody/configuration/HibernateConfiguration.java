package com.nobody.configuration;

import com.nobody.entity.Header;
import com.nobody.entity.ScheduleSettings;
import com.nobody.entity.TelegramCredentials;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
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
        .addAnnotatedClass(ScheduleSettings.class)
        .buildSessionFactory();
  }

  @Bean
  public Session session() throws URISyntaxException {
    return sessionFactory().openSession();
  }

  @Bean
  public DataSource dataSource() {
    return new HikariDataSource();
  }

  private Properties hibernateProperties() throws URISyntaxException {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    properties.setProperty("hibernate.show_sql", "true");
    properties.setProperty("hibernate.format_sql", "true");
    properties.setProperty(
        "hibernate.connection.url",
        "jdbc:postgresql://ep-orange-cherry-254155.eu-central-1.aws.neon.tech/neondb?user=pavelshinkarev209&password=9CK0MypRbPYt");
    return properties;
  }
}
