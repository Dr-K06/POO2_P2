package org.dao;

import jakarta.transaction.SystemException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.model.Professor;
import org.utils.HibernateUtil;

import java.util.List;

public class ProfessorDAO {

    public void salarProfessor(Professor professor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //Inicia Transação
            transaction = session.beginTransaction();
            session.persist(professor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Professor buscarProfessorPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.find(Professor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Professor> buscarProfessores(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("from Professor",Professor.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void atualizarProfessor(Professor professor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(professor);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deletarProfessor(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Professor professor =session.find(Professor.class, id);

            if(professor!=null){
                session.remove(professor);
            }
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
