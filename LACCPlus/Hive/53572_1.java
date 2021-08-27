//,temp,GenTezUtils.java,704,758,temp,GenTezUtils.java,621,679
//,3
public class xxx {
  public static void removeSemiJoinOperator(ParseContext context,
          AppMasterEventOperator eventOp, TableScanOperator ts) throws SemanticException{
    // Cleanup the synthetic predicate in the tablescan operator and filter by
    // replacing it with "true"
    LOG.debug("Removing AppMasterEventOperator " + eventOp + " and TableScan " + ts);
    ExprNodeDesc constNode = new ExprNodeConstantDesc(
            TypeInfoFactory.booleanTypeInfo, Boolean.TRUE);
    // Retrieve generator
    DynamicPruningEventDesc dped = (DynamicPruningEventDesc) eventOp.getConf();
    // TS operator
    DynamicPartitionPrunerContext filterDynamicListPredicatesCollection =
            new DynamicPartitionPrunerContext();
    if (ts.getConf().getFilterExpr() != null) {
      collectDynamicPruningConditions(
              ts.getConf().getFilterExpr(), filterDynamicListPredicatesCollection);
      for (DynamicListContext ctx : filterDynamicListPredicatesCollection) {
        if (ctx.generator != dped.getGenerator()) {
          continue;
        }
        // remove the condition by replacing it with "true"
        if (ctx.grandParent == null) {
          // This was the only predicate, set filter expression to const
          ts.getConf().setFilterExpr(null);
        } else {
          int i = ctx.grandParent.getChildren().indexOf(ctx.parent);
          ctx.grandParent.getChildren().remove(i);
          ctx.grandParent.getChildren().add(i, constNode);
        }
      }
    }
    // Filter operator
    filterDynamicListPredicatesCollection.dynLists.clear();
    for (Operator<?> op : ts.getChildOperators()) {
      if (!(op instanceof FilterOperator)) {
        continue;
      }
      FilterDesc filterDesc = ((FilterOperator) op).getConf();
      collectDynamicPruningConditions(
              filterDesc.getPredicate(), filterDynamicListPredicatesCollection);
      for (DynamicListContext ctx : filterDynamicListPredicatesCollection) {
        if (ctx.generator != dped.getGenerator()) {
          continue;
        }
        // remove the condition by replacing it with "true"
        if (ctx.grandParent == null) {
          // This was the only predicate, set filter expression to const
          filterDesc.setPredicate(constNode);
        } else {
          int i = ctx.grandParent.getChildren().indexOf(ctx.parent);
          ctx.grandParent.getChildren().remove(i);
          ctx.grandParent.getChildren().add(i, constNode);
        }
      }
    }
  }

};