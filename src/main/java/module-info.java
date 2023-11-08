module br.fipp.sisdentalfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.logging;
    requires org.json;

    opens br.fipp.sisdentalfx to javafx.fxml;
    opens br.fipp.sisdentalfx.db.entidades to javafx.fxml;
    exports br.fipp.sisdentalfx;
    exports br.fipp.sisdentalfx.db.entidades;
}