package com.gvs.config.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
 * en la base de datos (crm).
 * Esta clase configura el EntityManagerFactory y el
 * JpaTransactionManager para manejar operaciones JPA.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.gvs.model.crm.repository", // Paquete donde se encuentran los repositorios JPA
        entityManagerFactoryRef = "crmEMF", // Referencia al EntityManagerFactory
        transactionManagerRef = "crmTrxManager" // Referencia al TransactionManager
)
@EnableTransactionManagement // Habilita la gestión transaccional en Spring
public class CRMConfig {

    @Autowired
    @Qualifier("crmDBDataSource")
    private DataSource dataSource; // Inyecta el DataSource configurado

    @Autowired
    private EntityManagerFactoryBuilder entityManagerFactoryBuilder;


    /**
     * Crea un LocalContainerEntityManagerFactoryBean configurado para CRM.
     *
     * @return Un LocalContainerEntityManagerFactoryBean configurado.
     */
    @Primary
    @Bean("crmEMF")
    public LocalContainerEntityManagerFactoryBean crmEntityManagerFactory() {
        Map<String, Object> additionalProps = new HashMap<>();
        additionalProps.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");

        System.out.println("Usando DataSource para CRM: ");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .packages("com.gvs.model.crm.entity")
                .persistenceUnit("CRM")
                .properties(additionalProps)
                .build();
    }


    /**
     * Crea un JpaTransactionManager que maneja las transacciones JPA para la unidad de persistencia.
     *
     * @param crmEMF El LocalContainerEntityManagerFactoryBean que se utiliza para obtener el EntityManager.
     * @return Un JpaTransactionManager configurado.
     */
    @Primary  // Indica que este es el bean principal a usar cuando hay múltiples opciones
    @Bean("crmTrxManager")
    public JpaTransactionManager getCrmTrxManager(@Qualifier("crmEMF") LocalContainerEntityManagerFactoryBean crmEMF) {
        return new JpaTransactionManager(crmEMF.getObject()); // Crea un Transaction Manager usando el Entity Manager Factory
    }
}
