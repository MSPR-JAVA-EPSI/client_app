package client_app.dto.in;

public class DtoInEquipement {
    private String id;
    private String name;
    private boolean available;

    public DtoInEquipement(String id, String name, Boolean available){
        this.id = id;
        this.name = name;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    @Override
    public String toString() {
        return "DtoInEquipement{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", available=" + available +
                '}';
    }
}
