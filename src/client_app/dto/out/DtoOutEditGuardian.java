package client_app.dto.out;

public class DtoOutEditGuardian {
    private int id;
    private String name;
    private boolean administrator;
    private String fullname;

    public DtoOutEditGuardian(int id, String name, boolean administrator, String fullname) {
        this.id = id;
        this.name = name;
        this.administrator = administrator;
        this.fullname = fullname;
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
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }
}
