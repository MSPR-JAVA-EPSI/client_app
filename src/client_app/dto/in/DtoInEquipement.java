package client_app.dto.in;

public class DtoInEquipement {
    private int id;
    private String name;
    private int quantity;

    public DtoInEquipement(int id, String name, int quantity){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "DtoInEquipement{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                '}';
    }
}
