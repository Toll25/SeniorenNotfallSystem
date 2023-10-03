package at.htlhl.MQTT;

public class Main {

    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static void main(String[] args) {
        Client client = new Client();
        client.init();
    }
}