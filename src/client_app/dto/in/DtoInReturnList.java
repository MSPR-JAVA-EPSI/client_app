package client_app.dto.in;

import java.util.List;

public class DtoInReturnList {
    private List<DtoInReturn> equipments;

    public DtoInReturnList(List<DtoInReturn> equipments) {
        this.equipments = equipments;
    }

    public List<DtoInReturn> getReturnList() {
        return equipments;
    }
}
