module com.example.queens_problem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens com.example.queens_problem to javafx.fxml;
    exports com.example.queens_problem;
}