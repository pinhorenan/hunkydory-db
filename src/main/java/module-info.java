module hunkydory {
    requires java.sql;
    requires javafx.controls;
    requires javafx.graphics;

    opens hunkydory to javafx.graphics;
}