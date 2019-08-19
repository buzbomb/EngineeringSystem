import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class BOM {
    private UUID id, product;
    private String productName;
    private Map<UUID, Integer> bomItems = new TreeMap();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProduct() {
        return product;
    }

    public void setProduct(UUID product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Map<UUID, Integer> getBomItems() {
        return bomItems;
    }

    public void setBomItems(Map<UUID, Integer> bomItems) {
        this.bomItems = bomItems;
    }
}
