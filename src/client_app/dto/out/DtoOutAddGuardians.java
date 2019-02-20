package client_app.dto.out;

import java.util.List;

public class DtoOutAddGuardians {
    private List<DtoOutAddGuardian> guardians;

    public DtoOutAddGuardians(List<DtoOutAddGuardian> guardians) {
        this.guardians = guardians;
    }
}
