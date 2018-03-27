package database.entity;

public interface IMessage extends Identified {

    public void setMessage(String message);

    public String getMessage();
}
