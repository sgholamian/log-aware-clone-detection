//,temp,sample_3148.java,2,16,temp,sample_3149.java,2,17
//,3
public class xxx {
public void dummy_method(){
ConstantPropagateProcCtx cppCtx = (ConstantPropagateProcCtx) ctx;
Map<ColumnInfo, ExprNodeDesc> constants = cppCtx.getPropagatedConstants(op);
cppCtx.getOpToConstantExprs().put(op, constants);
if (constants.isEmpty()) {
return null;
}
if (op.getChildOperators().size() == 1 && op.getChildOperators().get(0) instanceof ReduceSinkOperator) {
return null;
}
if (LOG.isInfoEnabled()) {


log.info("old exprs");
}
}

};