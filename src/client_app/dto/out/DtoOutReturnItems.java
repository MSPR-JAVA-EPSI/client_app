package client_app.dto.out;

import java.util.List;

public class DtoOutReturnItems {
    private List<DtoOutReturnItem> equipments;

    public DtoOutReturnItems(List<DtoOutReturnItem> equipments) {
        this.equipments = equipments;
    }

    public List<DtoOutReturnItem> getDtoOutReturnItems() {
        return equipments;
    }

    public void setDtoOutReturnItems(List<DtoOutReturnItem> equipments) {
        this.equipments = equipments;
    }
}
