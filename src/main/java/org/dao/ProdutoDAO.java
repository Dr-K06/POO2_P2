package org.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.model.Produto;
import org.utils.HibernateUtil; // Mantenha esta dependência

import java.util.List;

public class ProdutoDAO {

    /**
     * Salva um novo Produto no banco de dados.
     * @param produto O objeto Produto a ser persistido.
     */
    public void salvar(Produto produto) { // CORRIGIDO: salarProfessor -> salvar
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Inicia Transação
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

    /**
     * Busca um Produto pelo seu ID.
     * @param id O ID do Produto.
     * @return O objeto Produto encontrado ou null.
     */
    public Produto buscarPorId(Long id) { // CORRIGIDO: buscarProfessorPorId -> buscarPorId
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Transações de leitura simples (find) não precisam necessariamente de commit
            // mas manter o beginTransaction é uma boa prática
            session.beginTransaction();
            return session.find(Produto.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todos os Produtos no banco de dados.
     * @return Uma lista de objetos Produto.
     */
    public List<Produto> buscarTodos(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("from Produto", Produto.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Atualiza um Produto existente.
     * @param produto O objeto Produto com os dados atualizados.
     */
    public void atualizar(Produto produto) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(produto);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Deleta um Produto pelo seu ID.
     * @param id O ID do Produto a ser deletado.
     */
    public void deletar(Long id) { // CORRIGIDO: deletarProfessor -> deletar
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Busca o objeto Produto a ser removido
            Produto produto = session.find(Produto.class, id); // CORRIGIDO: professor -> produto

            if(produto != null){ // CORRIGIDO: professor -> produto
                session.remove(produto); // CORRIGIDO: professor -> produto
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