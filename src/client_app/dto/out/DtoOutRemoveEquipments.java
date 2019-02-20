package client_app.dto.out;

import java.util.List;

public class DtoOutRemoveEquipments {

    private List<DtoOutRemoveEquipment> equipments;

    public DtoOutRemoveEquipments(List<DtoOutRemoveEquipment> equipments) {
        this.equipments = equipments;
    }
}
