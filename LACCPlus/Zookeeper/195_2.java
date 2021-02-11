//,temp,WriteLock.java,152,161,temp,DistributedQueue.java,220,224
//,3
public class xxx {
        public void process(WatchedEvent event){
            LOG.debug("Watcher fired on path: " + event.getPath() + " state: " + 
                    event.getState() + " type " + event.getType());
            latch.countDown();
        }

};