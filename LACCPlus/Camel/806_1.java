//,temp,JGroupsConsumer.java,54,60,temp,JGroupsConsumer.java,46,52
//,3
public class xxx {
    @Override
    protected void doStop() throws Exception {
        LOG.debug("Closing connection to cluster: {} from receiver: {}.", clusterName, receiver);
        endpoint.getResolvedChannel().setReceiver(null);
        endpoint.disconnect();
        super.doStop();
    }

};