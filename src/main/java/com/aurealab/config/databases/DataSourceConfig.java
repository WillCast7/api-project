package com.aurealab.config.databases;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Configuración de las fuentes de datos y el EntityManagerFactoryBuilder.
 */
@Configuration
public class DataSourceConfig {

    /**
     * Crea un bean de DataSourceProperties para la base de datos CRM.
     *
     * @return Un objeto DataSourceProperties configurado.
     */
    @Bean("aureaDBProperties")
    @ConfigurationProperties(prefix = "spring.datasource.aurea")
    public DataSourceProperties getAureaProperties() {
        return new DataSourceProperties();
    }

    /**
     * Crea un bean de DataSource utilizando las propiedades definidas para CRM.
     *
     * @return Un objeto DataSource inicializado.
     */
    @Bean("aureaDBDataSource")
    @Primary
    public DataSource getAureaDataSource() {
        return getAureaProperties().initializeDataSourceBuilder().build();
    }

/*

    @Bean("crmDBProperties")
    @ConfigurationProperties(prefix = "spring.datasource.crm")
    public DataSourceProperties getCrmProperties() {
        return new DataSourceProperties();
    }

    @Bean("crmDBDataSource")
    @Primary
    public DataSource getCrmDataSource() {
        return getCrmProperties().initializeDataSourceBuilder().build();
    }


    @Bean("sapDBProperties")
    @ConfigurationProperties(prefix = "spring.datasource.sap")
    public DataSourceProperties getSapProperties() {
        return new DataSourceProperties();
    }

    @Bean("sapDBDataSource")
    public DataSource getSapDataSource() {
        return getSapProperties().initializeDataSourceBuilder().build();
    }


    @Bean("emarketDBProperties")
    @ConfigurationProperties(prefix = "spring.datasource.emarket")
    public DataSourceProperties getEMarketProperties() {
        return new DataSourceProperties();
    }

    @Bean("emarketDBDataSource")
    public DataSource getEMarketDataSource() {
        return getEMarketProperties().initializeDataSourceBuilder().build();
    }
*/



    /**
     * Crea un EntityManagerFactoryBuilder que se utiliza para construir
     * el EntityManagerFactory. Este builder permite configurar opciones adicionales.
     *
     * @return Un objeto EntityManagerFactoryBuilder.
     */
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }
}
