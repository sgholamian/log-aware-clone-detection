//,temp,Jt400MsgQueueService.java,76,84,temp,Jt400DataQueueService.java,82,90
//,2
public class xxx {
    @Override
    public void stop() {
        if (queue != null) {
            LOG.debug("Releasing connection to {}", endpoint);
            AS400 system = queue.getSystem();
            queue = null;
            endpoint.releaseSystem(system);
        }
    }

};