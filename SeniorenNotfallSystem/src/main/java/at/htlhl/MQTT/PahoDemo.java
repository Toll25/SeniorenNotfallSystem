package at.htlhl.MQTT;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class PahoDemo implements MqttCallback {

    MqttClient client;

    public PahoDemo() {
    }

    public static void main(String[] args) {
        new PahoDemo().doDemo();
    }

    public void doDemo() {
        try {
            String username   = "itp-project-1@ttn";
            String password   = "NNSXS.CCEKXFMR74QKCMM7ZRJ2SXCLVDTKFTZXYGMKNLY.NEZDSO3KPCTOZ6M2A3J6RPLL4O6J635HKMJFEZ7VUQDBUZMWLWNQ";
            String serverurl  = "tcp://eu1.cloud.thethings.network:1883";
            String clientId   = MqttClient.generateClientId();

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            client = new MqttClient(serverurl, clientId, null);
            client.connect(options);

            client.setCallback(this);
            client.subscribeWithResponse("itp-project-1/devices/#");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        System.out.println(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

}