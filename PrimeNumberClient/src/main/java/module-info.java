module clientfx {
    requires  javafx.controls;
    requires  java.sql;

    opens PrimeNumberClient to javafx.fxml;
    exports PrimeNumberClient;
}