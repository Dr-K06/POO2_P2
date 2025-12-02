package org.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static void main (String[] args){

        EntityManagerFactory enf = Persistence.createEntityManagerFactory("un-jpa");
        EntityManager em = enf.createEntityManager();
        em.getTransaction().begin();
        Usuario usuario = new Usuario();
        usuario.setNome("Carlos Eduardo S. Costa");
        usuario.setEmail("ocarlosdigital09@gmail.com");
        usuario.setSenha("123456");
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();

    }
}
