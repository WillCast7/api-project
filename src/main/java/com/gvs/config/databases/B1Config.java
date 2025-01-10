package com.gvs.config.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuración para la gestión de entidades y transacciones
 * en la base de datos (B1).
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.gvs.model.b1.repository",
        entityManagerFactoryRef = "b1EMF",
        transactionManagerRef = "b1TrxManager"
)
@EnableTransactionManagement
public class B1Config {

    @Autowired
    @Qualifier("b1DBDataSource")
    private DataSource dataSource; // Inyecta el DataSource configurado

    @Autowired
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder; // Inyecta el EntityManagerFactoryBuilder

    /**
     * Crea un LocalContainerEntityManagerFactoryBean configurado para B1.
     *
     * @return Un LocalContainerEntityManagerFactoryBean configurado.
     */
    @Bean("b1EMF")
    public LocalContainerEntityManagerFactoryBean b1EntityManagerFactory() {
        Map<String, Object> additionalProps = new HashMap<>();
        additionalProps.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");

        System.out.println("Usando DataSource para B1: ");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.gvs.model.b1.entity")
                .persistenceUnit("B1")
                .properties(additionalProps)
                .build();
    }

    /**
     * Crea un JpaTransactionManager para B1.
     *
     * @return Un JpaTransactionManager configurado.
     */
    @Bean("b1TrxManager")
    public JpaTransactionManager b1TrxManager(@Qualifier("b1EMF") LocalContainerEntityManagerFactoryBean b1EMF) {
        return new JpaTransactionManager(b1EMF.getObject());
    }
}
