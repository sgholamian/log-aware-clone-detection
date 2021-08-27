//,temp,sample_2330.java,2,12,temp,sample_2329.java,2,12
//,2
public class xxx {
public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procContext, Object... nodeOutputs) throws SemanticException {
GenSparkProcContext context = (GenSparkProcContext) procContext;
Preconditions.checkArgument(context != null, "AssertionError: expected context to be not null");
Preconditions.checkArgument(context.currentTask != null, "AssertionError: expected context.currentTask to be not null");
Preconditions.checkArgument(context.currentRootOperator != null, "AssertionError: expected context.currentRootOperator to be not null");
Operator<? extends OperatorDesc> operator = (Operator<? extends OperatorDesc>) nd;
Operator<?> root = context.currentRootOperator;


log.info("leaf operator");
}

};