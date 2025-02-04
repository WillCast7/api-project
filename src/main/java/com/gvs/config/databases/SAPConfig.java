package com.gvs.config.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuración para la gestión de entidades y transacciones
 * en la base de datos (SAP).
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.gvs.model.sap.repository",
        entityManagerFactoryRef = "sapEMF",
        transactionManagerRef = "sapTrxManager"
)
@EnableTransactionManagement
public class SAPConfig {

    @Autowired
    @Qualifier("sapDBDataSource")
    private DataSource dataSource; // Inyecta el DataSource configurado

    @Autowired
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder; // Inyecta el EntityManagerFactoryBuilder

    /**
     * Crea un LocalContainerEntityManagerFactoryBean configurado para SAP.
     *
     * @return Un LocalContainerEntityManagerFactoryBean configurado.
     */
    @Bean("sapEMF")
    public LocalContainerEntityManagerFactoryBean sapEntityManagerFactory() {
        Map<String, Object> additionalProps = new HashMap<>();
        additionalProps.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");

        System.out.println("Usando DataSource para SAP: ");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.gvs.model.sap.entity")
                .persistenceUnit("SAP")
                .properties(additionalProps)
                .build();
    }

    /**
     * Crea un JpaTransactionManager para SAP.
     *
     * @return Un JpaTransactionManager configurado.
     */
    @Bean("sapTrxManager")
    public JpaTransactionManager sapTrxManager(@Qualifier("sapEMF") LocalContainerEntityManagerFactoryBean sapEMF) {
        return new JpaTransactionManager(sapEMF.getObject());
    }
}
