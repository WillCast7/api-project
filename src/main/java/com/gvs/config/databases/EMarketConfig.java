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
 * en la base de datos (EMarket).
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.gvs.model.emarket.repository",
        entityManagerFactoryRef = "emarketEMF",
        transactionManagerRef = "emarketTrxManager"
)
@EnableTransactionManagement
public class EMarketConfig {

    @Autowired
    @Qualifier("emarketDBDataSource")
    private DataSource dataSource; // Inyecta el DataSource configurado

    @Autowired
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder; // Inyecta el EntityManagerFactoryBuilder

    /**
     * Crea un LocalContainerEntityManagerFactoryBean configurado para EMarket.
     *
     * @return Un LocalContainerEntityManagerFactoryBean configurado.
     */
    @Bean("emarketEMF")
    public LocalContainerEntityManagerFactoryBean emarketEntityManagerFactory() {
        Map<String, Object> additionalProps = new HashMap<>();
        additionalProps.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");

        System.out.println("Usando DataSource para EMarket: ");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.gvs.model.emarket.entity")
                .persistenceUnit("emarket")
                .properties(additionalProps)
                .build();
    }

    /**
     * Crea un JpaTransactionManager para EMarket.
     *
     * @return Un JpaTransactionManager configurado.
     */
    @Bean("emarketTrxManager")
    public JpaTransactionManager emarketpTrxManager(@Qualifier("emarketEMF") LocalContainerEntityManagerFactoryBean emarketEMF) {
        return new JpaTransactionManager(emarketEMF.getObject());
    }
}
