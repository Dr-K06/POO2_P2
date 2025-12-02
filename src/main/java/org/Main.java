package org;

import org.dao.ProdutoDAO;
import org.model.Produto;
import org.utils.HibernateUtil;

public class Main {

    public static void main (String[] args){

        /*
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
 */
        ProdutoDAO dao = new ProdutoDAO();

        System.out.println("------[ Create Professor ] -------");
        Produto professor = new Produto();
        professor.setNome("DANIEL C. SILVA");
        professor.setPreco(0.0);
        professor.setQuantidade(0);
        dao.salarProfessor(professor);

        System.out.println("");
        System.out.println("------[ Buscar por ID do Professor ] -------");
        Produto produtoAux = dao.buscarProfessorPorId(1L);
        System.out.println(produtoAux);

        System.out.println("");
        System.out.println("------[ Update Professor ] -------");
        if (produtoAux != null) {
            professor.setPreco(0.0);
            professor.setQuantidade(0);
            professor.setNome("Miguel C. SILVA");
            System.out.printf("Professor atualizado: " + dao.buscarProfessorPorId(1L));
        }

        System.out.println("");
        System.out.println("------[ Listar todos Professores ] -------");
        dao.buscarProfessores().forEach(System.out::println);

        System.out.println("");
        System.out.println("------[ Deletar Professor ] -------");
        if (dao.buscarProfessorPorId(1L) != null) {
            dao.deletarProfessor(1L);
        }
        HibernateUtil.shutdown();
    }
}
