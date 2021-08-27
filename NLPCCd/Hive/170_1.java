//,temp,sample_1258.java,2,10,temp,sample_1257.java,2,10
//,2
public class xxx {
public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procContext, Object... nodeOutputs) throws SemanticException {
GenTezProcContext context = (GenTezProcContext) procContext;
assert context != null && context.currentTask != null && context.currentRootOperator != null;
Operator<?> operator = (Operator<?>) nd;
Operator<?> root = context.currentRootOperator;


log.info("leaf operator");
}

};