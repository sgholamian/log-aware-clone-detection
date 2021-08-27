//,temp,IrcLogger.java,116,119,temp,IrcLogger.java,111,114
//,3
public class xxx {
    @Override
    public void onTopic(String chan, IRCUser user, String topic) {
        log.debug("Server: {} - onTopic chan={} user={} topic={}", server, chan, user, topic);
    }

};