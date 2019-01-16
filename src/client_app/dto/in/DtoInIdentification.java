package client_app.dto.in;

public class DtoInIdentification {
    private String token;

    public DtoInIdentification(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "DtoInIdentification{" +
                "token='" + token + '\'' +
                '}';
    }
}
