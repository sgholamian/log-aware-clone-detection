//,temp,StatsRulesProcFactory.java,271,324,temp,StatsRulesProcFactory.java,192,235
//,3
public class xxx {
    @Override
    public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx,
        Object... nodeOutputs) throws SemanticException {
      AnnotateStatsProcCtx aspCtx = (AnnotateStatsProcCtx) procCtx;
      FilterOperator fop = (FilterOperator) nd;
      Operator<? extends OperatorDesc> parent = fop.getParentOperators().get(0);
      Statistics parentStats = parent.getStatistics();
      List<String> neededCols = null;
      if (parent instanceof TableScanOperator) {
        TableScanOperator tsop = (TableScanOperator) parent;
        neededCols = tsop.getNeededColumns();
      }

      if (parentStats != null) {
        ExprNodeDesc pred = fop.getConf().getPredicate();

        // evaluate filter expression and update statistics
        aspCtx.clearAffectedColumns();
        long newNumRows = evaluateExpression(parentStats, pred, aspCtx,
            neededCols, fop, parentStats.getNumRows());
        Statistics st = parentStats.clone();

        if (satisfyPrecondition(parentStats)) {

          // update statistics based on column statistics.
          // OR conditions keeps adding the stats independently, this may
          // result in number of rows getting more than the input rows in
          // which case stats need not be updated
          if (newNumRows <= parentStats.getNumRows()) {
            StatsUtils.updateStats(st, newNumRows, true, fop, aspCtx.getAffectedColumns());
          }

          if (LOG.isDebugEnabled()) {
            LOG.debug("[0] STATS-" + fop.toString() + ": " + st.extendedToString());
          }
        } else {

          // update only the basic statistics in the absence of column statistics
          if (newNumRows <= parentStats.getNumRows()) {
            StatsUtils.updateStats(st, newNumRows, false, fop);
          }

          if (LOG.isDebugEnabled()) {
            LOG.debug("[1] STATS-" + fop.toString() + ": " + st.extendedToString());
          }
        }

        st = applyRuntimeStats(aspCtx.getParseContext().getContext(), st, fop);
        fop.setStatistics(st);

        aspCtx.setAndExprStats(null);
      }
      return null;
    }

};