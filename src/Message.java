public class Message {
    private int messageType;
    private String data;

    public Message(int messageType,String data){
        this.messageType = messageType;
        this.data = data;
    }

    public int getMessageType() {
        return messageType;
    }

    public String getData() {
        return data;
    }
}
