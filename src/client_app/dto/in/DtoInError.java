package client_app.dto.in;

public class DtoInError {
    private String error;

    public DtoInError(String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "DtoInError{" +
                "error='" + error + '\'' +
                '}';
    }
}
