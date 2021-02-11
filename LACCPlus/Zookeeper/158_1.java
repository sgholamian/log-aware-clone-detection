//,temp,ACLTest.java,182,193,temp,RecoveryTest.java,201,208
//,3
public class xxx {
    public void process(WatchedEvent event) {
        LOG.info("Event:" + event.getState() + " " + event.getType() + " "
                 + event.getPath());
        if (event.getState() == KeeperState.SyncConnected) {
            if (startSignal != null && startSignal.getCount() > 0) {
                LOG.info("startsignal.countDown()");
                startSignal.countDown();
            } else {
                LOG.warn("startsignal " + startSignal);
            }
        }
    }

};