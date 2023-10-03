package at.htlhl.seniorennotfallsystemfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ClientController {
    @FXML
    private Label emergencyStatusLabel;
    @FXML
    private Label ledStatusLabel;

    @FXML
    protected void confirmEmergency () {
        Main.client.respond(1);
        setLedStatus("ON");
    }

    @FXML
    protected void cancelEmergencyResponse () {
        Main.client.respond(0);
        setLedStatus("OFF");
    }

    public void setEmergencyStatus(String text){
        emergencyStatusLabel.setText(text);
    }

    public void setLedStatus(String text){
        ledStatusLabel.setText(text);
    }
}