package exceptions;


public class AddPhotoException extends Exception {

    private String type;

    public AddPhotoException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

