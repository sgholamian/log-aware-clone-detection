//,temp,sample_3.java,2,17,temp,sample_5219.java,2,14
//,3
public class xxx {
public ParseContext transform(ParseContext pctx) throws SemanticException {
Map<Rule, NodeProcessor> opRules = Maps.newLinkedHashMap();
opRules.put(new RuleRegExp("R1", TableScanOperator.getOperatorName() + "%"), new Processor());
WalkerCtx context = new WalkerCtx(pctx.getConf());
Dispatcher disp = new DefaultRuleDispatcher(null, opRules, context);
List<Node> topNodes = Lists.newArrayList();
topNodes.addAll(pctx.getTopOps().values());
GraphWalker walker = new PreOrderWalker(disp);
walker.startWalking(topNodes, null);


log.info("tablepropertyenrichmentoptimizer transform complete");
}

};