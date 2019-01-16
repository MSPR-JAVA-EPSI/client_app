package client_app.dto.out;

public class DtoOutIdentification {
    private String image;

    public DtoOutIdentification(String image){
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
