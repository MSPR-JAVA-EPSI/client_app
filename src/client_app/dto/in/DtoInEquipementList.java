package client_app.dto.in;

import java.util.List;

public class DtoInEquipementList {
    private List<DtoInEquipement> equipements;

    public DtoInEquipementList(List<DtoInEquipement> equipements){
        this.equipements = equipements;
    }

    public List<DtoInEquipement> getEquipements() {
        return equipements;
    }

    @Override
    public String toString() {
        return "DtoInEquipementList{" +
                "equipements=" + equipements +
                '}';
    }
}
