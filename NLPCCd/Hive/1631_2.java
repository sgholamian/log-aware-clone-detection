//,temp,sample_3903.java,2,17,temp,sample_3902.java,2,16
//,3
public class xxx {
public void dummy_method(){
TableScanOperator operator = (TableScanOperator) nd;
List<Node> opChildren = operator.getChildren();
TableScanDesc operatorDesc = operator.getConf();
if (operatorDesc == null || !tsToIndices.containsKey(operator)) {
return null;
}
List<Index> indexes = tsToIndices.get(operator);
ExprNodeDesc predicate = operatorDesc.getFilterExpr();
IndexWhereProcCtx context = (IndexWhereProcCtx) procCtx;
ParseContext pctx = context.getParseContext();


log.info("processing predicate for index optimization");
}

};