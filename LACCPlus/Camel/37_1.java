//,temp,IrcLogger.java,86,89,temp,IrcLogger.java,81,84
//,2
public class xxx {
    @Override
    public void onPart(String chan, IRCUser user, String msg) {
        log.debug("Server: {} - onPart chan={} user={} msg=\"{}\"", server, chan, user, msg);
    }

};