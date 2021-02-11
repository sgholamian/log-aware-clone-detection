//,temp,RaceConditionTest.java,91,105,temp,EphemeralNodeDeletionTest.java,168,178
//,3
public class xxx {
    @After
    public void tearDown() {
        // stop all severs
        for (int i = 0; i < mt.length; i++) {
            try {
                mt[i].shutdown();
            } catch (InterruptedException e) {
                LOG.warn("Quorum Peer interrupted while shutting it down", e);
            }
        }
    }

};