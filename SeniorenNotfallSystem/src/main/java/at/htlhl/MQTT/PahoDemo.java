package at.htlhl.MQTT;

import org.eclipse.paho.client.mqttv3.*;

public class PahoDemo implements MqttCallback {

    MqttClient client;

    static String username = "itp-project-1@ttn";
    static String password = "NNSXS.PJMOQAXSX3JI6UGAQAP5E3S2T633VDXSVQLMRUQ.Q7SZXOGSKKQPZCYX45N2R52EXSI2CTYHTYBNC27CJNVPZN6SCZKA";

    public PahoDemo() {
    }

    public static void main(String[] args) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(60);
        options.setKeepAliveInterval(60);
        new PahoDemo().doDemo(options);
    }

    public void doDemo(MqttConnectOptions options) {

        try {
            client = new MqttClient("tcp://eu1.cloud.thethings.network:1883", "Sending");
            client.connect(options);
            client.setCallback(this);
            if(client.isConnected()){
                System.out.println("Bit CONNECTTTTT");
            }
            //client.subscribe("message");
            MqttMessage message = new MqttMessage();
            message.setPayload("A single message from my computer fff"
                    .getBytes());
            Thread.sleep(1000);
            client.publish("message", message);
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub
        System.out.println("###########Disconnected##############");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        System.out.println(message);
        System.out.println("###########messageArrived##############");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        System.out.println("###########deliveryComplete##############");
    }

}