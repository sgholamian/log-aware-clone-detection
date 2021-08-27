//,temp,SimplePredicatePushDown.java,54,108,temp,PredicatePushDown.java,88,145
//,3
public class xxx {
  @Override
  public ParseContext transform(ParseContext pctx) throws SemanticException {
    pGraphContext = pctx;

    // create a the context for walking operators
    OpWalkerInfo opWalkerInfo = new OpWalkerInfo(pGraphContext);

    Map<SemanticRule, SemanticNodeProcessor> opRules = new LinkedHashMap<SemanticRule, SemanticNodeProcessor>();
    opRules.put(new RuleRegExp("R1",
      FilterOperator.getOperatorName() + "%"),
      OpProcFactory.getFilterSyntheticJoinPredicateProc());
    opRules.put(new RuleRegExp("R2",
      PTFOperator.getOperatorName() + "%"),
      OpProcFactory.getPTFProc());
    opRules.put(new RuleRegExp("R3",
      CommonJoinOperator.getOperatorName() + "%"),
      OpProcFactory.getJoinProc());
    opRules.put(new RuleRegExp("R4",
      TableScanOperator.getOperatorName() + "%"),
      OpProcFactory.getTSProc());
    opRules.put(new RuleRegExp("R5",
      ScriptOperator.getOperatorName() + "%"),
      OpProcFactory.getSCRProc());
    opRules.put(new RuleRegExp("R6",
      LimitOperator.getOperatorName() + "%"),
      OpProcFactory.getLIMProc());
    opRules.put(new RuleRegExp("R7",
      UDTFOperator.getOperatorName() + "%"),
      OpProcFactory.getUDTFProc());
    opRules.put(new RuleRegExp("R8",
      LateralViewForwardOperator.getOperatorName() + "%"),
      OpProcFactory.getLVFProc());
    opRules.put(new RuleRegExp("R9",
      LateralViewJoinOperator.getOperatorName() + "%"),
      OpProcFactory.getLVJProc());
    opRules.put(new RuleRegExp("R10",
        ReduceSinkOperator.getOperatorName() + "%"),
        OpProcFactory.getRSProc());

    // The dispatcher fires the processor corresponding to the closest matching
    // rule and passes the context along
    SemanticDispatcher disp = new DefaultRuleDispatcher(OpProcFactory.getDefaultProc(),
        opRules, opWalkerInfo);
    SemanticGraphWalker ogw = new DefaultGraphWalker(disp);

    // Create a list of topop nodes
    ArrayList<Node> topNodes = new ArrayList<Node>();
    topNodes.addAll(pGraphContext.getTopOps().values());
    ogw.startWalking(topNodes, null);

    if (LOG.isDebugEnabled()) {
      LOG.debug("After PPD:\n" + Operator.toString(pctx.getTopOps().values()));
    }
    return pGraphContext;
  }

};