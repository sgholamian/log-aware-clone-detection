//,temp,ZooKeeperProducer.java,251,262,temp,ZooKeeperProducer.java,237,249
//,3
public class xxx {
    private OperationResult synchronouslyDelete(ProductionContext ctx) throws Exception {
        DeleteOperation setData = new DeleteOperation(ctx.connection, ctx.node);
        setData.setVersion(ctx.version);

        OperationResult result = setData.get();

        if (!result.isOk() && configuration.isCreate() && result.failedDueTo(Code.NONODE)) {
            LOG.warn(format("Node '%s' did not exist, creating it.", ctx.node));
            result = createNode(ctx);
        }
        return result;
    }

};