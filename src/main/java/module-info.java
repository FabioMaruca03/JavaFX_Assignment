module assignment {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens comp1110.ass2.gui to javafx.fxml, javafx.graphics, javafx.controls;

    exports comp1110.ass2.gui;
}