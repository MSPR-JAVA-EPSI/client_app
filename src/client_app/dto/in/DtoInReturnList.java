package client_app.dto.in;

import java.util.List;

public class DtoInReturnList {
    private List<DtoInReturn> returnList;

    public DtoInReturnList(List<DtoInReturn> returnList) {
        this.returnList = returnList;
    }

    public List<DtoInReturn> getReturnList() {
        return returnList;
    }
}
