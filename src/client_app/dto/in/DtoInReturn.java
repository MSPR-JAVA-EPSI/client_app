package client_app.dto.in;

public class DtoInReturn {
    private int id;
    private String name;
    private int quantity;

    public DtoInReturn(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
