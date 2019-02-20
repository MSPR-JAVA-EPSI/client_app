package client_app.dto.out;

import java.util.List;

public class DtoOutBorrowItems {
    private List<DtoOutBorrowItem> equipments;

    public DtoOutBorrowItems(List<DtoOutBorrowItem> equipments) {
        this.equipments = equipments;
    }

    public List<DtoOutBorrowItem> getEquipments() {
        return equipments;
    }
}
