//,temp,ZooKeeperProducer.java,135,140,temp,ZooKeeperProducer.java,127,133
//,3
public class xxx {
    private void asynchronouslySetDataOnNode(ZooKeeper connection, ProductionContext context) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(format("Storing data to node '%s', not waiting for confirmation", context.node));
        }
        connection.setData(context.node, context.payload, context.version, new AsyncSetDataCallback(), context);
    }

};