package org.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.model.Produto;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // --- CONFIGURAÇÃO DO BANCO DE DADOS DIRETO NO CÓDIGO ---
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/db-java");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "postgres"); // <--- CONFIRA SUA SENHA

            // O Dialeto que estava faltando
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            // Opções úteis
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // --- REGISTRO DAS CLASSES (Importante!) ---
            // Você deve adicionar uma linha para cada @Entity do seu projeto
            configuration.addAnnotatedClass(Produto.class);


            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Erro crítico na criação da SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}