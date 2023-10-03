module at.htlhl.seniorennotfallsystemfx {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens at.htlhl.seniorennotfallsystemfx to javafx.fxml;
    exports at.htlhl.seniorennotfallsystemfx;
}