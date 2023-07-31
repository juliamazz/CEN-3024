module wordfx {
    requires  javafx.controls;
    requires  javafx.fxml;
    requires  java.sql;

    opens WordFrequencyAnalyzer to javafx.fxml;
    exports WordFrequencyAnalyzer;
}