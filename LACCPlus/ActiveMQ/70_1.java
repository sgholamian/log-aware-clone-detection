//,temp,MemoryPropertyTest.java,55,62,temp,DestinationWildcardTest.java,53,63
//,3
public class xxx {
    @Override
    protected void tearDown() throws Exception {
        LOG.info("Closing Broker");
        if (broker != null) {
            broker.stop();
        }
        LOG.info("Broker closed...");
    }

};