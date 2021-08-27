//,temp,IrcLogger.java,71,74,temp,IrcLogger.java,66,69
//,3
public class xxx {
    @Override
    public void onMode(IRCUser user, String passiveNick, String mode) {
        log.info("Server: {} - onMode user={} passiveNick={} mode={}", server, user, passiveNick, mode);
    }

};