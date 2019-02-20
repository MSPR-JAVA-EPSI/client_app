package client_app.dto.out;

public class DtoOutEditEquipment {
    private int id;
    private String name;
    private int quantity;

    public DtoOutEditEquipment(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}
