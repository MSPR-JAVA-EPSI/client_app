package client_app.dto.out;

public class DtoOutAddGuardian {
    private String name;
    private String image;
    private String fullname;
    private boolean administrator;

    public DtoOutAddGuardian(String name, String image, String fullname, boolean administrator) {
        this.name = name;
        this.image = image;
        this.fullname = fullname;
        this.administrator = administrator;
    }

}
