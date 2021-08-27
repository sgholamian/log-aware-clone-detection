//,temp,IrcLogger.java,116,119,temp,IrcLogger.java,111,114
//,3
public class xxx {
    @Override
    public void onReply(int num, String value, String msg) {
        log.debug("Server: {} - onReply num={} value=\"{}\" msg=\"{}\"", server, num, value, msg);
    }

};