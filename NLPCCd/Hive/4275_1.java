//,temp,sample_3148.java,2,16,temp,sample_3149.java,2,17
//,3
public class xxx {
public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx ctx, Object... nodeOutputs) throws SemanticException {
JoinOperator op = (JoinOperator) nd;
JoinDesc conf = op.getConf();
ConstantPropagateProcCtx cppCtx = (ConstantPropagateProcCtx) ctx;
Map<ColumnInfo, ExprNodeDesc> constants = cppCtx.getPropagatedConstants(op);
cppCtx.getOpToConstantExprs().put(op, constants);
if (constants.isEmpty()) {
return null;
}
if (op.getChildOperators().size() == 1 && op.getChildOperators().get(0) instanceof ReduceSinkOperator) {


log.info("skip join rs structure");
}
}

};