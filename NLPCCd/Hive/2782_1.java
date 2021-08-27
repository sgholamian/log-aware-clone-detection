//,temp,sample_3145.java,2,11,temp,sample_3139.java,2,13
//,3
public class xxx {
public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx ctx, Object... nodeOutputs) throws SemanticException {
Operator<?> op = (Operator<?>) nd;
ConstantPropagateProcCtx cppCtx = (ConstantPropagateProcCtx) ctx;
cppCtx.getOpToConstantExprs().put(op, new HashMap<ColumnInfo, ExprNodeDesc>());
if (LOG.isDebugEnabled()) {


log.info("stop propagate constants on op");
}
}

};