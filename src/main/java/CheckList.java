import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CheckList {
    private UUID checkListId, order;
    private Map<UUID, Integer> theList = new HashMap<>();

    public void addToCheckList(UUID partId, int quantity){
        this.theList.put(partId, quantity);
    }

    public void sendCheckList(String checkListString){
        String topic        = "stockCheck";
        int qos             = 2;
        String broker       = "tcp://192.168.1.4:1883";
        String clientId     = "SalesSystem";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            connOpts.setAutomaticReconnect(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing file: "+ checkListString);
            MqttMessage message = new MqttMessage(checkListString.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
        theList.clear();
    }

    public UUID getCheckListId() {
        return checkListId;
    }

    public void setCheckListId(UUID checkListId) {
        this.checkListId = checkListId;
    }

    public UUID getOrder() {
        return order;
    }

    public void setOrder(UUID order) {
        this.order = order;
    }

    public Map<UUID, Integer> getTheList() {
        return theList;
    }

    public void setTheList(Map<UUID, Integer> theList) {
        this.theList = theList;
    }
}
