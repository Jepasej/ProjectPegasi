module org.example.projectpegasi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.projectpegasi to javafx.fxml;
    exports org.example.projectpegasi;
}