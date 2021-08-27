//,temp,sample_943.java,2,17,temp,sample_4750.java,2,17
//,2
public class xxx {
public void dummy_method(){
opRules.put(new RuleRegExp("R7", UDTFOperator.getOperatorName() + "%"), OpProcFactory.getUDTFProc());
opRules.put(new RuleRegExp("R8", LateralViewForwardOperator.getOperatorName() + "%"), OpProcFactory.getLVFProc());
opRules.put(new RuleRegExp("R9", LateralViewJoinOperator.getOperatorName() + "%"), OpProcFactory.getLVJProc());
opRules.put(new RuleRegExp("R10", ReduceSinkOperator.getOperatorName() + "%"), OpProcFactory.getRSProc());
Dispatcher disp = new DefaultRuleDispatcher(OpProcFactory.getDefaultProc(), opRules, opWalkerInfo);
GraphWalker ogw = new DefaultGraphWalker(disp);
ArrayList<Node> topNodes = new ArrayList<Node>();
topNodes.addAll(pGraphContext.getTopOps().values());
ogw.startWalking(topNodes, null);
if (LOG.isDebugEnabled()) {


log.info("after ppd");
}
}

};