module hunkydory {
    requires java.sql;
    requires javafx.controls;
    requires javafx.graphics;
    requires io.github.cdimascio.dotenv.java;

    opens hunkydory to javafx.graphics;
}