module org.jp_p2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.transaction;


    opens org.jp_p2 to javafx.fxml;
    exports org.jp_p2;
    opens org.model to
            org.hibernate.orm.core, // Módulo principal do Hibernate
            jakarta.persistence;

}