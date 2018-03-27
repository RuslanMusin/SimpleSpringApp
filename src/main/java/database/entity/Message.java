package database.entity;

public class Message implements IMessage{

    private Integer id;

    private String message;

    public Message() {
    }

    public Message(String message) {
        this.message = message;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
