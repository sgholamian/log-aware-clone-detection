//,temp,IrcLogger.java,61,64,temp,IrcLogger.java,51,54
//,3
public class xxx {
    @Override
    public void onKick(String chan, IRCUser user, String passiveNick, String msg) {
        log.debug("Server: {} - onKick chan={} user={} passiveNick={} msg=\"{}\"", server, chan, user, passiveNick, msg);
    }

};