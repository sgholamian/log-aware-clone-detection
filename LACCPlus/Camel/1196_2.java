//,temp,ZooKeeperProducer.java,251,262,temp,ZooKeeperProducer.java,237,249
//,3
public class xxx {
    private OperationResult synchronouslySetData(ProductionContext ctx) throws Exception {

        SetDataOperation setData = new SetDataOperation(ctx.connection, ctx.node, ctx.payload);
        setData.setVersion(ctx.version);

        OperationResult result = setData.get();

        if (!result.isOk() && configuration.isCreate() && result.failedDueTo(Code.NONODE)) {
            LOG.warn(format("Node '%s' did not exist, creating it.", ctx.node));
            result = createNode(ctx);
        }
        return result;
    }

};