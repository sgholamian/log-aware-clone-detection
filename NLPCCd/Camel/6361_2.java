//,temp,sample_7907.java,2,11,temp,sample_7906.java,2,11
//,3
public class xxx {
private OperationResult synchronouslySetData(ProductionContext ctx) throws Exception {
SetDataOperation setData = new SetDataOperation(ctx.connection, ctx.node, ctx.payload);
setData.setVersion(ctx.version);
OperationResult result = setData.get();
if (!result.isOk() && configuration.isCreate() && result.failedDueTo(Code.NONODE)) {


log.info("node s did not exist creating it");
}
}

};