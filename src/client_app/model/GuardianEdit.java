package client_app.model;

public class GuardianEdit {
    private int id;
    private String name;
    private boolean administrator;

    public GuardianEdit(int id, String name, boolean administrator) {
        this.id = id;
        this.name = name;
        this.administrator = administrator;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
