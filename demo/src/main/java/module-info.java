module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.opencsv;

    opens org.example.demo to javafx.fxml; // Asegúrate de que el paquete esté abierto
    exports org.example.demo; // Exporta el paquete principal
}