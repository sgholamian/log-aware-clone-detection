//,temp,sample_485.java,2,13,temp,sample_675.java,2,13
//,2
public class xxx {
public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx, Object... nodeOutputs) throws SemanticException {
FilterOperator filterOp = (FilterOperator) nd;
ExprNodeDesc predicate = filterOp.getConf().getPredicate();
ExprNodeDesc newPredicate = generateInClause(predicate);
if (newPredicate != null) {
if (LOG.isDebugEnabled()) {


log.info("generated new predicate with in clause");
}
}
}

};