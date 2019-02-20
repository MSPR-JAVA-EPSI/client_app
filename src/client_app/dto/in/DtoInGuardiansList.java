package client_app.dto.in;

import java.util.List;

public class DtoInGuardiansList {
    private List<DtoInGuardian> guardians;

    public DtoInGuardiansList(List<DtoInGuardian> guardians) {
        this.guardians = guardians;
    }

    public List<DtoInGuardian> getGuardians() {
        return guardians;
    }
}
