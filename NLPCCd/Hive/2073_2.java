//,temp,sample_3127.java,2,19,temp,sample_3125.java,2,19
//,2
public class xxx {
public void dummy_method(){
if (desc instanceof ExprNodeGenericFuncDesc) {
ExprNodeGenericFuncDesc funcDesc = (ExprNodeGenericFuncDesc) desc;
GenericUDF udf = funcDesc.getGenericUDF();
boolean propagateNext = propagate && propagatableUdfs.contains(udf.getClass());
List<ExprNodeDesc> newExprs = new ArrayList<ExprNodeDesc>();
for (ExprNodeDesc childExpr : desc.getChildren()) {
newExprs.add(foldExpr(childExpr, constants, cppCtx, op, tag, propagateNext));
}
if (!isConstantFoldableUdf(udf, newExprs)) {
if (LOG.isDebugEnabled()) {


log.info("function is undeterministic don t evaluate immediately");
}
}
}
}

};