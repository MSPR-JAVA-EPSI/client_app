package client_app.dto.in;

public class DtoInIdentification {
    private String token;
    private String fullname;
    private String image;
    private boolean administrator;

    public DtoInIdentification(String token, String fullname, String image, boolean administrator){
        this.token = token;
        this.fullname = fullname;
        this.image = image;
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

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
