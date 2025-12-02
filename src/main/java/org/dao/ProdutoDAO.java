package org.dao;

import jakarta.transaction.SystemException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.model.Produto;
import org.utils.HibernateUtil;

import java.util.List;

public class ProdutoDAO {

    public void salarProfessor(Produto produto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //Inicia Transação
            transaction = session.beginTransaction();
            session.persist(produto);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Produto buscarProfessorPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.find(Produto.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Produto> buscarProfessores(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("from Professor",Produto.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void atualizarProfessor(Produto professor) {
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
            Produto professor =session.find(Produto.class, id);

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
