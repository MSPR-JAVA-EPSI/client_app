package client_app.dto.out;

import java.util.List;

public class DtoOutAddEquipments {
    private List<DtoOutAddEquipment> equipments;

    public DtoOutAddEquipments(List<DtoOutAddEquipment> equipments) {
        this.equipments = equipments;
    }
}
