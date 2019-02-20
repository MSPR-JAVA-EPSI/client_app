package client_app.dto.out;

import java.util.List;

public class DtoOutEditGuardians {
    private List<DtoOutEditGuardian> guardians;

    public DtoOutEditGuardians(List<DtoOutEditGuardian> guardians) {
        this.guardians = guardians;
    }

    public List<DtoOutEditGuardian> getGuardians() {
        return guardians;
    }
}
