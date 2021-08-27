//,temp,ZooKeeperProducer.java,264,272,temp,ZooKeeperProducer.java,203,212
//,3
public class xxx {
        @Override
        public void processResult(int rc, String path, Object ctx) {
            if (LOG.isDebugEnabled()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace(format("Removed data node '%s'", path));
                } else {
                    LOG.debug(format("Removed data node '%s'", path));
                }
            }
        }

};