//,temp,sample_3150.java,2,19,temp,sample_4077.java,2,19
//,3
public class xxx {
public void dummy_method(){
int tag = e.getKey();
List<ExprNodeDesc> exprs = e.getValue();
if (exprs == null) {
continue;
}
List<ExprNodeDesc> newExprs = new ArrayList<ExprNodeDesc>();
for (ExprNodeDesc expr : exprs) {
ExprNodeDesc newExpr = foldExpr(expr, constants, cppCtx, op, tag, false);
if (newExpr instanceof ExprNodeConstantDesc) {
if (LOG.isInfoEnabled()) {


log.info("expr fold from is removed");
}
}
}
}

};