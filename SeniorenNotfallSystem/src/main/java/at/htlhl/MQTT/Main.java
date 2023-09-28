package at.htlhl.MQTT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;

public class Main {

    static String serverURI = "tcp://eu1.cloud.thethings.network:1883"; // TTN MQTT server URI
    static String clientId = "TestClientID"; // Unique client ID
    static String username = "itp-project-1@ttn"; // TTN Application ID
    static String password = "NNSXS.7FYSGXPXGD3KHCKUTGIDUSLNG7OMMY2D7LGIS6Y.SIDOXV3NJHDPX3SF5OMHRO52JFQE56JPRSKPB5N7HC6HVKE6P2QA"; // TTN Access Key
    static String subTopic = "v3/itp-project-1@ttn/devices/uno-0004a30b001c1b03/up";
    static String resTopic = "v3/itp-project-1@ttn/devices/uno-0004a30b001c1b03/down/push";
    static MqttClient client;

    static {
        try {
            client = new MqttClient(serverURI, clientId, new MemoryPersistence());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            client.connect(options);

            if (client.isConnected()) {
                System.out.println("connected");
            }
            // Subscribe to a TTN topic (e.g., application's uplink topic)
            client.subscribe(subTopic);

            // Define a callback for incoming messages
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.getMessage());
                    cause.printStackTrace();
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("Received message on topic: " + topic);
                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = new String((message.getPayload()));
                    JsonNode jsonNode = objectMapper.readTree(json);
                    //String response = );
                    String response = (((jsonNode.get("uplink_message")).get("decoded_payload")).get("value")).toPrettyString();
                    System.out.println("Message payload: " + response);

                    if (response.equals("\"notfall\"")) {
                        respond(0);
                    } else if (response.equals("\"ok\"")) {
                        respond(1);
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Not used in this example
                }
            });

            // Keep the program running to receive messages
        } catch (MqttException e) {
            System.err.println("MQTT Error Code: " + e.getReasonCode());
            System.err.println("MQTT Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void respond(int code) {
        MqttMessage message = new MqttMessage();

        String payload = "{\n \"app\": \"senior\",\n \"type\": \"led\",\n\"value\": " + code + "\n}";
        message.setPayload(payload.getBytes());
        System.out.println(new String(message.getPayload()));
        try {
            client.publish(resTopic, message);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}