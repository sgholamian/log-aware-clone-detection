//,temp,IrcLogger.java,61,64,temp,IrcLogger.java,51,54
//,3
public class xxx {
    @Override
    public void onInvite(String chan, IRCUser user, String passiveNick) {
        log.debug("Server: {} - onInvite chan={} user={} passiveNick={}", server, chan, user, passiveNick);
    }

};