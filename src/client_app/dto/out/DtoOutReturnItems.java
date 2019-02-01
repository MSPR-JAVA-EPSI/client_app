package client_app.dto.out;

import java.util.List;

public class DtoOutReturnItems {
    private List<DtoOutReturnItem> dtoOutReturnItems;

    public DtoOutReturnItems(List<DtoOutReturnItem> dtoOutReturnItems) {
        this.dtoOutReturnItems = dtoOutReturnItems;
    }

    public List<DtoOutReturnItem> getDtoOutReturnItems() {
        return dtoOutReturnItems;
    }

    public void setDtoOutReturnItems(List<DtoOutReturnItem> dtoOutReturnItems) {
        this.dtoOutReturnItems = dtoOutReturnItems;
    }
}
