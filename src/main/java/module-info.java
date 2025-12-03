module org.jp_p2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    // Dependências do Banco
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.transaction;

    // Adicione dependências SQL básicas se necessário (opcional, mas recomendado)
    requires java.sql;

    // Libera o Controller para o FXML
    opens org.jp_p2 to javafx.fxml;
    exports org.jp_p2;

    // --- A CORREÇÃO ESTÁ AQUI EMBAIXO ---
    // Você precisa adicionar 'javafx.base' na lista de permissões do org.model
    opens org.model to
            javafx.base,             // <--- ESTA LINHA FALTAVA! (Para a Tabela funcionar)
            org.hibernate.orm.core,  // (Para o Banco funcionar)
            jakarta.persistence;

    // Se tiver outras classes na raiz 'org', mantenha isso, senão pode remover
    opens org to jakarta.persistence, org.hibernate.orm.core;
}