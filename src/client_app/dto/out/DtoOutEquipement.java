package client_app.dto.out;

public class DtoOutEquipement {
    private String token;

    public DtoOutEquipement(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
