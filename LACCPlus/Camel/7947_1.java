//,temp,DefaultConsumerCache.java,204,215,temp,DefaultProducerCache.java,411,424
//,3
public class xxx {
    @Override
    public synchronized void purge() {
        try {
            consumers.stop();
            consumers.start();
        } catch (Exception e) {
            LOG.debug("Error restarting consumer pool", e);
        }
        if (statistics != null) {
            statistics.clear();
        }
    }

};