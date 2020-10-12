module assignment {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens comp1110.ass2.gui to javafx.fxml, javafx.graphics, javafx.controls, java.base;

    exports comp1110.ass2.gui;
}