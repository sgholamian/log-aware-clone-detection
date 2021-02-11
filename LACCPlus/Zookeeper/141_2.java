//,temp,RecoveryTest.java,201,208,temp,SessionTest.java,167,175
//,3
public class xxx {
        public void process(WatchedEvent event) {
            LOG.info(name + " event:" + event.getState() + " "
                    + event.getType() + " " + event.getPath());
            if (event.getState() == KeeperState.SyncConnected
                    && startSignal != null && startSignal.getCount() > 0)
            {
                startSignal.countDown();
            }
        }

};