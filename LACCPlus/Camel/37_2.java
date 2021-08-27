//,temp,IrcLogger.java,86,89,temp,IrcLogger.java,81,84
//,2
public class xxx {
    @Override
    public void onNotice(String target, IRCUser user, String msg) {
        log.debug("Server: {} - onNotice target={} user={} msg=\"{}\"", server, target, user, msg);
    }

};