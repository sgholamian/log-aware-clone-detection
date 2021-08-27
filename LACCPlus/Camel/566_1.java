//,temp,ZooKeeperProducer.java,118,125,temp,ZooKeeperProducer.java,110,116
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();
        if (LOG.isTraceEnabled()) {
            LOG.trace(String.format("Shutting down zookeeper producer of '%s'", configuration.getPath()));
        }
        zkm.shutdown();
    }

};