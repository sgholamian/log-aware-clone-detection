//,temp,CommitProcessorTest.java,507,515,temp,FollowerRequestProcessor.java,145,151
//,3
public class xxx {
    public void shutdown() {
        LOG.info("Shutting down");
        finished = true;
        queuedRequests.clear();
        queuedRequests.add(Request.requestOfDeath);
        nextProcessor.shutdown();
    }

};