import java.util.UUID;

public class Part {
    private UUID partId;
    private String partName, description;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getPartId() {
        return partId;
    }

    public void setPartId(UUID partid) {
        this.partId = partid;
    }
}
