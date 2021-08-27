//,temp,ZooKeeperProducer.java,135,140,temp,ZooKeeperProducer.java,127,133
//,3
public class xxx {
    private void asynchronouslyDeleteNode(ZooKeeper connection, ProductionContext context) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(format("Deleting node '%s', not waiting for confirmation", context.node));
        }
        connection.delete(context.node, context.version, new AsyncDeleteCallback(), context);

    }

};