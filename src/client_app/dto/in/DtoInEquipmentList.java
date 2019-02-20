package client_app.dto.in;

import java.util.List;

public class DtoInEquipmentList {
    private List<DtoInEquipment> equipments;

    public DtoInEquipmentList(List<DtoInEquipment> equipements) {
        this.equipments = equipements;
    }

    public List<DtoInEquipment> getEquipements() {
        return equipments;
    }

    @Override
    public String toString() {
        return "DtoInEquipementList{" +
                "equipements=" + equipments +
                '}';
    }
}
