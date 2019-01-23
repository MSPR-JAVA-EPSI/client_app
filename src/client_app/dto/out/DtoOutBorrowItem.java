package client_app.dto.out;

public class DtoOutBorrowItem {
    private int quantity;
    private int id;

    public DtoOutBorrowItem(int quantity, int id) {
        this.quantity = quantity;
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }
}
