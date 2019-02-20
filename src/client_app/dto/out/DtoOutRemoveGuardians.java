package client_app.dto.out;

import java.util.List;

public class DtoOutRemoveGuardians {
    List<DtoOutRemoveGuardian> guardians;

    public DtoOutRemoveGuardians(List<DtoOutRemoveGuardian> guardians){
        this.guardians = guardians;
    }
}
