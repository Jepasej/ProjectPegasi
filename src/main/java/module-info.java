module org.example.projectpegasi {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.sql;
    requires jdk.httpserver;


    opens org.example.projectpegasi.Application to javafx.fxml, javafx.graphics;
    exports org.example.projectpegasi.Application; exports org.example.projectpegasi;
    exports org.example.projectpegasi.DomainModels;
}