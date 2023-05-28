module com.example.guifinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.guifinal to javafx.fxml;
    exports com.example.guifinal;
    exports com.example.guifinal.Customer;
    opens com.example.guifinal.Customer to javafx.fxml;
    exports com.example.guifinal.Item;
    opens com.example.guifinal.Item to javafx.fxml;
    exports com.example.guifinal.Controller;
    opens com.example.guifinal.Controller to javafx.fxml;

}