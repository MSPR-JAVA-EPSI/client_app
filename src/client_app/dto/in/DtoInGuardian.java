package client_app.dto.in;

public class DtoInGuardian {
    private int id;
    private String name;
    private boolean administrator;
    private String fullName;

    public DtoInGuardian(int id, String name, boolean administrator, String fullName) {
        this.id = id;
        this.name = name;
        this.administrator = administrator;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
