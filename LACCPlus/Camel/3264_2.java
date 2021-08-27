//,temp,ElasticsearchProducer.java,334,344,temp,ZooKeeperServiceDiscovery.java,86,101
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        super.doStop();

        if (serviceDiscovery != null) {
            try {
                serviceDiscovery.close();
            } catch (Exception e) {
                LOGGER.warn("Error closing Curator ServiceDiscovery", e);
            }
        }

        if (curator != null && managedInstance) {
            curator.close();
        }
    }

};