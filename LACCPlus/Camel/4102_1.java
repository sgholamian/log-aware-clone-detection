//,temp,Jt400MsgQueueService.java,60,74,temp,Jt400DataQueueService.java,62,80
//,3
public class xxx {
    @Override
    public void start() {
        if (queue == null) {
            AS400 system = endpoint.getSystem();
            queue = new MessageQueue(system, endpoint.getObjectPath());
        }
        if (!queue.getSystem().isConnected(AS400.COMMAND)) {
            LOG.debug("Connecting to {}", endpoint);
            try {
                queue.getSystem().connectService(AS400.COMMAND);
            } catch (Exception e) {
                throw RuntimeCamelException.wrapRuntimeCamelException(e);
            }
        }
    }

};