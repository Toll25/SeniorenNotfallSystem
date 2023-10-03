package at.htlhl.seniorennotfallsystemfx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Client {
    String serverURI = "tcp://eu1.cloud.thethings.network:1883";
    String clientId = "TestClientID";

    //login data
    String username = "itp-project-1@ttn";
    String password = "NNSXS.7FYSGXPXGD3KHCKUTGIDUSLNG7OMMY2D7LGIS6Y.SIDOXV3NJHDPX3SF5OMHRO52JFQE56JPRSKPB5N7HC6HVKE6P2QA";

    //topic on which to subscribe
    String subTopic = "v3/itp-project-1@ttn/devices/uno-0004a30b001c1b03/up";

    //topic on which to respond
    String resTopic = "v3/itp-project-1@ttn/devices/uno-0004a30b001c1b03/down/push";

    MqttClient client;

    public Client() {
        try {
            client = new MqttClient(serverURI, clientId, new MemoryPersistence());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() {
        MqttConnectOptions options = new MqttConnectOptions();

        options.setUserName(username);
        options.setPassword(password.toCharArray());

        try {
            client.connect(options);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        if (client.isConnected()) {
            System.out.println("CONNECTION ESTABLISHED");
        }

        // Subscribe to a TTN topic (e.g., application's uplink topic)
        try {
            client.subscribe(subTopic);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }

        // Define a callback for incoming messages
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost: " + cause.getMessage());
                cause.printStackTrace();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = new String((message.getPayload()));
                JsonNode jsonNode = objectMapper.readTree(json);
                String response = (((jsonNode.get("uplink_message")).get("decoded_payload")).get("value")).toPrettyString();

                if (response.equals("\"notfall\"")) {
                    Main.clientController.setEmergencyStatus("NOTFALL");
                } else if (response.equals("\"ok\"")) {
                    Main.clientController.setEmergencyStatus("OK");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Not used in this example
            }
        });
    }

    public void respond(int code) {
        MqttMessage message = new MqttMessage();
        String payload = """
                {
                  "downlinks": [
                    {
                      "decoded_payload": {
                          "app": "senior",
                          "type": "led",
                          "value": ${code}
                      },
                      "f_port": 1,
                      "priority": "NORMAL"
                    }
                  ]
                }
                """;
        payload = payload.replace("${code}", String.valueOf(code));
        message.setPayload(payload.getBytes());
        try {
            client.publish(resTopic, message);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

}
