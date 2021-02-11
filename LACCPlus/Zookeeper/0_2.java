//,temp,DistributedQueue.java,220,224,temp,RemoveWatchesCmdTest.java,325,332
//,3
public class xxx {
        public void process(WatchedEvent event) {
            LOG.debug("Event path : {}, eventPath : {}"
                    + new Object[] { path, event.getPath() });
            this.eventPath = event.getPath();
            if (expectedEvents.contains(event.getType())) {
                latch.countDown();
            }
        }

};