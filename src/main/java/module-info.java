module org.jp_p2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.jp_p2 to javafx.fxml;
    exports org.jp_p2;
}