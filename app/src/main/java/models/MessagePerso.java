package models;

public class MessagePerso {
    String message;
    String date;
    int direction;
    String receiver , sender;

    public MessagePerso(String message, String date, int direction) {
        this.message = message;
        this.date = date;
        this.direction = direction;
    }

    public MessagePerso(String message, String date, int direction, String receiver, String sender) {
        this.message = message;
        this.date = date;
        this.direction = direction;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
