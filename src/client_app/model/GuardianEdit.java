package client_app.model;

public class GuardianEdit {
    private int id;
    private String identifier;
    private String fullName;
    private boolean administrator;

    public GuardianEdit(int id, String identifier, String fullName, boolean administrator) {
        this.id = id;
        this.identifier = identifier;
        this.fullName = fullName;
        this.administrator = administrator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
