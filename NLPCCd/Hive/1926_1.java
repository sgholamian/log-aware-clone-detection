//,temp,sample_3.java,2,17,temp,sample_5219.java,2,14
//,3
public class xxx {
public void dummy_method(){
CorrelationNodeProcCtx corrCtx = new CorrelationNodeProcCtx(pCtx);
Map<Rule, NodeProcessor> opRules = new LinkedHashMap<Rule, NodeProcessor>();
opRules.put(new RuleRegExp("R1", ReduceSinkOperator.getOperatorName() + "%"), new CorrelationNodeProc());
Dispatcher disp = new DefaultRuleDispatcher(getDefaultProc(), opRules, corrCtx);
GraphWalker ogw = new DefaultGraphWalker(disp);
List<Node> topNodes = new ArrayList<Node>();
topNodes.addAll(pCtx.getTopOps().values());
ogw.startWalking(topNodes, null);
abort = corrCtx.isAbort();
if (abort) {


log.info("abort reasons are");
}
}

};