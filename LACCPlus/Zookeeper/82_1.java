//,temp,FollowerResyncConcurrencyTest.java,695,704,temp,ClientTest.java,246,255
//,3
public class xxx {
        public void process(WatchedEvent event) {
            super.process(event);
            if (event.getType() != Event.EventType.None) {
                try {
                    events.put(event);
                } catch (InterruptedException e) {
                    LOG.warn("ignoring interrupt during event.put");
                }
            }
        }

};