import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MQTTHandler implements MqttCallback {
    private ObjectMapper om = new ObjectMapper();
    private Order comparableOrder = new Order();
    private CheckList checkList = new CheckList();
    private Map<UUID, Part> parts;
    private  Map<UUID, BOM> theCatalogue;
    private int tempQuantity;
    String checkListString;

    public void subscribeToOrder(){

        String topic        = "orders";
        String broker       = "tcp://192.168.1.4:1883";
        String clientId     = "EngineeringSystem";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            connOpts.setAutomaticReconnect(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.setCallback(this);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            sampleClient.subscribe(topic);
            Thread.sleep(5000);
        } catch(MqttException | InterruptedException me) {
            System.out.println("reason "+me);
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost! " + cause);
        subscribeToOrder();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        byte[] json = message.getPayload();
        this.setComparableOrder(om.readValue(json, Order.class));

        try{
            theCatalogue = om.<List<BOM>>readValue(MQTTHandler.class.getResourceAsStream("/catalogue.json"), new TypeReference<List<BOM>>(){})
                    .stream()
                    .collect(Collectors.toMap(BOM::getProduct, Function.identity()));
            parts = om.<List<Part>>readValue(MQTTHandler.class.getResourceAsStream("/partList.json"), new TypeReference<List<Part>>() {})
                    .stream()
                    .collect(Collectors.toMap(Part::getPartId, Function.identity()));
        }catch(IOException e){
            e.printStackTrace();
        }
        checkList.setCheckListId(UUID.randomUUID());
        checkList.setOrder(comparableOrder.getId());


        for(Map.Entry<UUID, Integer> objectEntry : this.comparableOrder.getItems().entrySet()){
            for(Map.Entry<UUID, BOM> bomItem : theCatalogue.entrySet()){
                if(bomItem.getKey().equals(objectEntry.getKey())){
                    for(Map.Entry<UUID, Integer> part : bomItem.getValue().getBomItems().entrySet()){
                        if(checkList.getTheList().containsKey(part.getKey())) {
                            tempQuantity = checkList.getTheList().get(part.getKey()) + (part.getValue() * objectEntry.getValue());
                            checkList.addToCheckList(part.getKey(), tempQuantity);
                            tempQuantity = 0;
                        } else {
                            tempQuantity = part.getValue() * objectEntry.getValue();
                            checkList.addToCheckList(part.getKey(), tempQuantity);
                            tempQuantity = 0;
                        }
                    }
                }
            }
        }
        checkListString = om.writeValueAsString(checkList);
        checkList.sendCheckList(checkListString);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void printOrder(Order o, Map<UUID, Product> products){
        System.out.println("Order:\t" + o.getId());
        System.out.println("Customer:\t" + o.getCustomer());
        System.out.println("Items:");
        o.getItems().forEach( (product, count) -> System.out.println("\t" + products.get(product).getName() + ": " + count));
    }

    public void printCheckList(CheckList c, Map<UUID, Part> parts){
        System.out.println("Order:\t " + c.getOrder());
        System.out.println("\t" + "Parts:");
        c.getTheList().forEach((part, count) -> System.out.println("\t" + parts.get(part).getPartName() + ": " + count));
    }

    public Order getComparableOrder() {
        return comparableOrder;
    }

    public void setComparableOrder(Order comparableOrder) {
        this.comparableOrder = comparableOrder;
    }
}
