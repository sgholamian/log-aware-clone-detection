//,temp,sample_3697.java,2,11,temp,sample_3696.java,2,11
//,2
public class xxx {
private void generateEventOperatorPlan(DynamicListContext ctx, ParseContext parseContext, TableScanOperator ts, String column, String columnType) {
Operator<? extends OperatorDesc> parentOfRS = ctx.generator.getParentOperators().get(0);
ExprNodeDesc key = ctx.generator.getConf().getKeyCols().get(ctx.desc.getKeyIndex());
ExprNodeDesc partKey = ctx.parent.getChildren().get(0);
if (LOG.isDebugEnabled()) {


log.info("partition key expr");
}
}

};