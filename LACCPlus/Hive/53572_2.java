//,temp,GenTezUtils.java,704,758,temp,GenTezUtils.java,621,679
//,3
public class xxx {
  public static void removeSemiJoinOperator(ParseContext context,
                                     ReduceSinkOperator rs,
                                     TableScanOperator ts) throws SemanticException{
    // Cleanup the synthetic predicate in the tablescan operator by
    // replacing it with "true"
    LOG.debug("Removing ReduceSink " + rs + " and TableScan " + ts);
    ExprNodeDesc constNode = new ExprNodeConstantDesc(
            TypeInfoFactory.booleanTypeInfo, Boolean.TRUE);
    // TS operator
    DynamicValuePredicateContext filterDynamicValuePredicatesCollection =
            new DynamicValuePredicateContext();
    if (ts.getConf().getFilterExpr() != null) {
      collectDynamicValuePredicates(ts.getConf().getFilterExpr(),
              filterDynamicValuePredicatesCollection);
      for (ExprNodeDesc nodeToRemove : filterDynamicValuePredicatesCollection
              .childParentMapping.keySet()) {
        // Find out if this synthetic predicate belongs to the current cycle
        if (removeSemiJoinPredicate(context, rs, nodeToRemove)) {
          ExprNodeDesc nodeParent = filterDynamicValuePredicatesCollection
                  .childParentMapping.get(nodeToRemove);
          if (nodeParent == null) {
            // This was the only predicate, set filter expression to null
            ts.getConf().setFilterExpr(null);
          } else {
            int i = nodeParent.getChildren().indexOf(nodeToRemove);
            nodeParent.getChildren().remove(i);
            nodeParent.getChildren().add(i, constNode);
          }
        }
      }
    }
    // Filter operator
    for (Operator<?> op : ts.getChildOperators()) {
      if (!(op instanceof FilterOperator)) {
        continue;
      }
      FilterDesc filterDesc = ((FilterOperator) op).getConf();
      filterDynamicValuePredicatesCollection = new DynamicValuePredicateContext();
      collectDynamicValuePredicates(filterDesc.getPredicate(),
              filterDynamicValuePredicatesCollection);
      for (ExprNodeDesc nodeToRemove : filterDynamicValuePredicatesCollection
              .childParentMapping.keySet()) {
        // Find out if this synthetic predicate belongs to the current cycle
        if (removeSemiJoinPredicate(context, rs, nodeToRemove)) {
          ExprNodeDesc nodeParent = filterDynamicValuePredicatesCollection
                  .childParentMapping.get(nodeToRemove);
          if (nodeParent == null) {
            // This was the only predicate, set filter expression to const
            filterDesc.setPredicate(constNode);
          } else {
            int i = nodeParent.getChildren().indexOf(nodeToRemove);
            nodeParent.getChildren().remove(i);
            nodeParent.getChildren().add(i, constNode);
          }
        }
      }
    }
    context.getRsToSemiJoinBranchInfo().remove(rs);
  }

};