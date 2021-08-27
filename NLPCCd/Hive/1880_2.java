//,temp,sample_3365.java,2,18,temp,sample_3420.java,2,18
//,3
public class xxx {
public void dummy_method(){
RowSchema rs = op.getSchema();
if (op.getParentOperators() == null) {
return constants;
}
List<Map<Integer, ExprNodeDesc>> parentsToConstant = new ArrayList<>();
boolean areAllParentsContainConstant = true;
boolean noParentsContainConstant = true;
for (Operator<?> parent : op.getParentOperators()) {
Map<ColumnInfo, ExprNodeDesc> constMap = opToConstantExprs.get(parent);
if (constMap == null) {


log.info("constant of op is not found");
}
}
}

};