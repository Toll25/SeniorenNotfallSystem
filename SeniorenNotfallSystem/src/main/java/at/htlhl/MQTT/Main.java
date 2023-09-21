package at.htlhl.MQTT;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main {



    public static void main(String[] args) {
        String broker = "tcp://eu1.cloud.thethings.network:1883";
        MqttConnectOptions options = new MqttConnectOptions();
        String username = "itp-project-1@ttn";
        String password = "NNSXS.CCEKXFMR74QKCMM7ZRJ2SXCLVDTKFTZXYGMKNLY.NEZDSO3KPCTOZ6M2A3J6RPLL4O6J635HKMJFEZ7VUQDBUZMWLWNQ";
        String clientid = "84847a26ff2d456aa422c99e348c9233";
        String topic = "ITP/MQTTTest/message";
        int qos = 0;
        try {
            MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
            // connect options
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            // setup callback
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
                    System.out.println("topic: " + topic);
                    System.out.println("Qos: " + message.getQos());
                    System.out.println("message content: " + new String(message.getPayload()));

                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(options);
            client.subscribe(topic, qos);

            String content = "Hawwo";

            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            // publish message
            client.publish(topic, message);
            System.out.println("Message published");
            System.out.println("topic: " + topic);
            System.out.println("message content: " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
