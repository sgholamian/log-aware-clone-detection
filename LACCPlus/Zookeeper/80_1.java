//,temp,ClientTest.java,246,255,temp,DisconnectedWatcherTest.java,44,53
//,3
public class xxx {
        public void process(WatchedEvent event) {
            super.process(event);
            if (event.getType() != EventType.None) {
                try {
                    events.put(event);
                } catch (InterruptedException e) {
                    LOG.warn("ignoring interrupt during event.put");
                }
            }
        }

};