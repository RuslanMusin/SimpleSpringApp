package exceptions;

public class AddRelatedEntitiesException extends Exception {

    private String type;

    public AddRelatedEntitiesException(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
