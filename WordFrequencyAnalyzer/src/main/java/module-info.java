module com.example.gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.WordFrequencyAnalyzer to javafx.fxml;
    exports com.example.WordFrequencyAnalyzer;
}