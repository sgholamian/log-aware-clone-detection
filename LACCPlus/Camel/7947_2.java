//,temp,DefaultConsumerCache.java,204,215,temp,DefaultProducerCache.java,411,424
//,3
public class xxx {
    @Override
    public synchronized void purge() {
        try {
            if (producers != null) {
                producers.stop();
                producers.start();
            }
        } catch (Exception e) {
            LOG.debug("Error restarting producers", e);
        }
        if (statistics != null) {
            statistics.clear();
        }
    }

};