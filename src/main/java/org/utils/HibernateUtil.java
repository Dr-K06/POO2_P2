package org.utils;

import lombok.Getter;
import org.model.Professor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Cria a SessionFactory a partir do hibernate.properties e das classes mapeadas
            Configuration configuration = new Configuration();

            // Adiciona a classe de Entidade
            configuration.addAnnotatedClass(Professor.class);

            // Configura a partir do hibernate.properties (lido automaticamente)
            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Falha na criação inicial da SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        // Fecha caches e pools de conexão
        getSessionFactory().close();
    }
}
