//,temp,DistributedQueue.java,220,224,temp,RemoveWatchesCmdTest.java,325,332
//,3
public class xxx {
        public void process(WatchedEvent event){
            LOG.debug("Watcher fired on path: " + event.getPath() + " state: " + 
                    event.getState() + " type " + event.getType());
            latch.countDown();
        }

};