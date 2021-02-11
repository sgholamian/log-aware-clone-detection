//,temp,ObserverRequestProcessor.java,150,156,temp,FollowerRequestProcessor.java,145,151
//,2
public class xxx {
    public void shutdown() {
        LOG.info("Shutting down");
        finished = true;
        queuedRequests.clear();
        queuedRequests.add(Request.requestOfDeath);
        nextProcessor.shutdown();
    }

};