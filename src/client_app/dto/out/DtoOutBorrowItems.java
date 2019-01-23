package client_app.dto.out;

import java.util.List;

public class DtoOutBorrowItems {
    private List<DtoOutBorrowItem> equipements;

    public DtoOutBorrowItems(List<DtoOutBorrowItem> equipements) {
        this.equipements = equipements;
    }

    public List<DtoOutBorrowItem> getEquipements() {
        return equipements;
    }
}
