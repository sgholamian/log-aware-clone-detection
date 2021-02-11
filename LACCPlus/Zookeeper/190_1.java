//,temp,RaceConditionTest.java,91,105,temp,ReconfigDuringLeaderSyncTest.java,193,205
//,3
public class xxx {
    @After
    public void tearDown() {
        // stop all severs
        if (null != mt) {
            for (int i = 0; i < SERVER_COUNT; i++) {
                try {
                    // With the defect, leader hangs here also, but with fix
                    // it does not
                    mt[i].shutdown();
                } catch (InterruptedException e) {
                    LOG.warn("Quorum Peer interrupted while shutting it down", e);
                }
            }
        }
    }

};