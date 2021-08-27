//,temp,JGroupsConsumer.java,54,60,temp,JGroupsConsumer.java,46,52
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        super.doStart();
        LOG.debug("Connecting receiver: {} to the cluster: {}.", receiver, clusterName);
        endpoint.getResolvedChannel().setReceiver(receiver);
        endpoint.connect();
    }

};