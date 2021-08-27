//,temp,Jt400MsgQueueService.java,60,74,temp,Jt400DataQueueService.java,62,80
//,3
public class xxx {
    @Override
    public void start() {
        if (queue == null) {
            AS400 system = endpoint.getSystem();
            if (endpoint.isKeyed()) {
                queue = new KeyedDataQueue(system, endpoint.getObjectPath());
            } else {
                queue = new DataQueue(system, endpoint.getObjectPath());
            }
        }
        if (!queue.getSystem().isConnected(AS400.DATAQUEUE)) {
            LOG.debug("Connecting to {}", endpoint);
            try {
                queue.getSystem().connectService(AS400.DATAQUEUE);
            } catch (Exception e) {
                throw RuntimeCamelException.wrapRuntimeCamelException(e);
            }
        }
    }

};