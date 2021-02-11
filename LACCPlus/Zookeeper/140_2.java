//,temp,DisconnectedWatcherTest.java,44,53,temp,WatcherTest.java,63,73
//,3
public class xxx {
        public void process(WatchedEvent event) {
            super.process(event);
            if (event.getType() != Event.EventType.None) {
                timeOfLastWatcherInvocation = System.currentTimeMillis();
                try {
                    events.put(event);
                } catch (InterruptedException e) {
                    LOG.warn("ignoring interrupt during event.put");
                }
            }
        }

};