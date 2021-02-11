//,temp,FollowerResyncConcurrencyTest.java,695,704,temp,WatchEventWhenAutoResetTest.java,55,65
//,3
public class xxx {
        @Override
        public void process(WatchedEvent event) {
            super.process(event);
            try {
                if (event.getType() != Event.EventType.None) {
                    dataEvents.put(event);
                }
            } catch (InterruptedException e) {
                LOG.warn("ignoring interrupt during EventsWatcher process");
            }
        }

};