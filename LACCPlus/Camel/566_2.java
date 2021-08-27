//,temp,ZooKeeperProducer.java,118,125,temp,ZooKeeperProducer.java,110,116
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        connection = zkm.getConnection();
        if (LOG.isTraceEnabled()) {
            LOG.trace(String.format("Starting zookeeper producer of '%s'", configuration.getPath()));
        }
    }

};