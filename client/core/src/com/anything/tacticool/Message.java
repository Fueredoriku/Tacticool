
/**
 * Represents a message transmitted between the server and the client
 * It allows to parse a string received into a unified message format 
 * And conversly to encode a message into a unified string representation
 */
public class Message {
    
    private final Command cmd;
    private final List<String> arguments;

    protected Message(Command cmd, List<String> arguments){
        this.cmd = cmd;
        this.arguments = arguments;
        this.raw = raw;
    }

    /**
     * From a raw string received, parse it into a Message
     * @param raw : the raw data received 
     * @return : a Message class corresponding to the parsed raw data
     */
    public Message parse(String raw){
        // TODO : From the raw received string parse into a Message and return it
        return null;
    }

    /**
     * Encode a Message class into a string of data to send over the network
     * @param m : the Message class to encode
     * @return : the encoded string ready to be sent
     */
    public String encode(Message m) {
        // TODO : encode the Message into a string that can be sent and return it
        retur null;
    }

    enum Command {
        AddPlayer, SendClientMoves, SendState; // More to be defined...
    }


}

/**
 * Builder for messages
 */
public class MessageBuilder {
    private Command cmd;
    private List<String> args;

    public MessageBuilder(){
        args = new ArrayList<>();
    }

    public void setCmd(Command cmd){
        this.cmd = cmd;
    }

    public void addArg(String arg){
        args.add(arg);
    }

    public Message build(){
        return new Message(cmd, args);
    }
}
