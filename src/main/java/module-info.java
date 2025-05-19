module org.example.projectpegasi {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens org.example.projectpegasi.Application to javafx.fxml, javafx.graphics;
    exports org.example.projectpegasi.Application; exports org.example.projectpegasi;
}