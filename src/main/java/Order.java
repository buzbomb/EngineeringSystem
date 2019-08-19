import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.UUID;

public class Order {
    private UUID id, customer;
    private Map<UUID, Integer> items = new TreeMap<>();

    /*public void newOrder(Map<UUID, Product> products){
        System.out.println("NEW ORDER" + "\n" + "Order Number: " + this.getId());
        Scanner sc = new Scanner(System.in);
        String addProductCheck = null;

        while(true){
            if(addProductCheck == null || addProductCheck.equals("Y") || addProductCheck.equals("y")){
                System.out.println("Pick product from list:" + "\n" + "1. Product 1" + "\n" + "2. Product 2" + "\n" + "3. Product 3" + "\n" + "Product:");
                int userSelection = sc.nextInt();

                switch (userSelection){
                    case 1:
                        tempProductId = UUID.fromString("26b1ac42-b251-4f4b-88d3-06aba35a6f6b");
                        break;
                    case 2:
                        tempProductId = UUID.fromString("70ba2d97-5130-421b-85ee-36be5833f38e");
                        break;
                    case 3:
                        tempProductId = UUID.fromString("5cdc6176-fff5-4afa-8a87-6eeab7aa4893");
                        break;
                }

                System.out.println("Quantity:");
                int quantity = sc.nextInt();

                items.put(tempProductId, quantity);
            } else {
                if(addProductCheck.equals("N") || addProductCheck.equals("n")){
                    break;
                }
            }

            System.out.println("Do you want to add another item Y/N?");
            sc.nextLine();
            addProductCheck = sc.nextLine();
        }
    }

    public void sendOrder(String orderString){

        String topic        = "orders";
        int qos             = 2;
        String broker       = "tcp://192.168.1.202:1883";
        String clientId     = "SalesSystem";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing file: "+ orderString);
            MqttMessage message = new MqttMessage(orderString.getBytes());
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
        items.clear();
    }*/

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomer() {
        return customer;
    }

    public void setCustomer(UUID customer) {
        this.customer = customer;
    }

    public Map<UUID, Integer> getItems() {
        return items;
    }

    public void setItems(Map<UUID, Integer> items) {
        this.items = items;
    }
}
