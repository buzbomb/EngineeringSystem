import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class EngineeringSystem {
    public static void main(String[] args) throws IOException {
        MQTTHandler theHandler = new MQTTHandler();
        theHandler.subscribeToOrder();
    }
}
