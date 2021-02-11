//,temp,ClientTest.java,246,255,temp,WatcherTest.java,63,73
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