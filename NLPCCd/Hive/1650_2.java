//,temp,sample_3135.java,2,17,temp,sample_3134.java,2,12
//,3
public class xxx {
private static ExprNodeDesc evaluateColumn(ExprNodeColumnDesc desc, ConstantPropagateProcCtx cppCtx, Operator<? extends Serializable> parent) {
RowSchema rs = parent.getSchema();
ColumnInfo ci = rs.getColumnInfo(desc.getColumn());
if (ci == null) {
if (LOG.isErrorEnabled()) {


log.info("reverse look up of column error");
}
}
}

};