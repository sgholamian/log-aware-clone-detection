//,temp,ElasticsearchProducer.java,334,344,temp,ZooKeeperServiceDiscovery.java,86,101
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        if (client != null) {
            LOG.info("Disconnecting from ElasticSearch cluster: {}", configuration.getClusterName());
            client.close();
            if (sniffer != null) {
                sniffer.close();
            }
        }
        super.doStop();
    }

};