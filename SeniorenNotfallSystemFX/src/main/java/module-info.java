module at.htlhl.seniorennotfallsystemfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    requires org.eclipse.paho.client.mqttv3;
            
    opens at.htlhl.seniorennotfallsystemfx to javafx.fxml;
    exports at.htlhl.seniorennotfallsystemfx;
}