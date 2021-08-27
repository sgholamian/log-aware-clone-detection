//,temp,CodehausIrcChat.java,53,59,temp,CodehausIrcChat.java,45,51
//,3
public class xxx {
        @Override
        public void onMode(String string, IRCUser ircUser, IRCModeParser ircModeParser) {
            super.onMode(string, ircUser, ircModeParser);
            LOG.info("onMode.string = " + string);
            LOG.info("onMode.ircUser = " + ircUser);
            LOG.info("onMode.ircModeParser = " + ircModeParser);
        }

};