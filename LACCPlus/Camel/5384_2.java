//,temp,IrcLogger.java,71,74,temp,IrcLogger.java,66,69
//,3
public class xxx {
    @Override
    public void onMode(String chan, IRCUser user, IRCModeParser ircModeParser) {
        log.info("Server: {} - onMode chan={} user={} ircModeParser={}", server, chan, user, ircModeParser);
    }

};