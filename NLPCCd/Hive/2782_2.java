//,temp,sample_3145.java,2,11,temp,sample_3139.java,2,13
//,3
public class xxx {
public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx ctx, Object... nodeOutputs) throws SemanticException {
FilterOperator op = (FilterOperator) nd;
ConstantPropagateProcCtx cppCtx = (ConstantPropagateProcCtx) ctx;
Map<ColumnInfo, ExprNodeDesc> constants = cppCtx.getPropagatedConstants(op);
cppCtx.getOpToConstantExprs().put(op, constants);
ExprNodeDesc condn = op.getConf().getPredicate();
if (LOG.isDebugEnabled()) {


log.info("old filter fil conditions");
}
}

};