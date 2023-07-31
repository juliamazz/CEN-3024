module serverfx {
    requires  javafx.controls;
    requires  java.sql;

    opens PrimeNumberServer to javafx.fxml;
    exports PrimeNumberServer;
}