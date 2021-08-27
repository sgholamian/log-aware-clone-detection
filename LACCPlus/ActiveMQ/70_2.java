//,temp,MemoryPropertyTest.java,55,62,temp,DestinationWildcardTest.java,53,63
//,3
public class xxx {
    @After
    public void tearDown() throws Exception {
        LOG.info("Shutting down");
        if (broker != null && broker.isStarted()) {
            LOG.info("Broker still running, stopping it now.");
            broker.stop();
        }
        else {
            LOG.info("Broker not running, nothing to shutdown.");
        }
    }

};