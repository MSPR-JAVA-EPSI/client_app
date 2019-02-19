package client_app.dto.in;

public class DtoInIdentification {
    private String token;
    private boolean administrator;

    public DtoInIdentification(String token, boolean administrator){
        this.token = token;
        this.administrator = administrator;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
