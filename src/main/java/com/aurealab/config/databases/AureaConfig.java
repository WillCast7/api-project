package com.aurealab.config.databases;

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
 * en la base de datos (Aurea).
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.aurealab.model.aurea.repository",
        entityManagerFactoryRef = "aureaEMF",
        transactionManagerRef = "aureaTrxManager"
)
@EnableTransactionManagement
public class AureaConfig {

    @Autowired
    @Qualifier("aureaDBDataSource")
    private DataSource dataSource; // Inyecta el DataSource configurado

    @Autowired
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder; // Inyecta el EntityManagerFactoryBuilder

    /**
     * Crea un LocalContainerEntityManagerFactoryBean configurado para Aurea.
     *
     * @return Un LocalContainerEntityManagerFactoryBean configurado.
     */
    @Bean("aureaEMF")
    public LocalContainerEntityManagerFactoryBean aureaEntityManagerFactory() {
        Map<String, Object> additionalProps = new HashMap<>();
        additionalProps.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");

        System.out.println("Usando DataSource para Aurea: ");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.aurealab.model.aurea.entity")
                .persistenceUnit("Aurea")
                .properties(additionalProps)
                .build();
    }

    /**
     * Crea un JpaTransactionManager para Aurea.
     *
     * @param aureaEMF El LocalContainerEntityManagerFactoryBean que se utiliza para obtener el EntityManager.
     * @return Un JpaTransactionManager configurado.
     */
    @Bean("aureaTrxManager")
    public JpaTransactionManager aureaTrxManager(@Qualifier("aureaEMF") LocalContainerEntityManagerFactoryBean aureaEMF) {
        return new JpaTransactionManager(aureaEMF.getObject());
    }
}
