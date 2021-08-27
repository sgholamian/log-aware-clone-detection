//,temp,StatsRulesProcFactory.java,271,324,temp,StatsRulesProcFactory.java,192,235
//,3
public class xxx {
    @Override
    public Object process(Node nd, Stack<Node> stack, NodeProcessorCtx procCtx,
        Object... nodeOutputs) throws SemanticException {

      SelectOperator sop = (SelectOperator) nd;
      Operator<? extends OperatorDesc> parent = sop.getParentOperators().get(0);
      Statistics parentStats = parent.getStatistics();
      AnnotateStatsProcCtx aspCtx = (AnnotateStatsProcCtx) procCtx;
      HiveConf conf = aspCtx.getConf();
      Statistics stats = null;

      if (parentStats != null) {
        stats = parentStats.clone();
      }

      if (satisfyPrecondition(parentStats)) {
        // this will take care of mapping between input column names and output column names. The
        // returned column stats will have the output column names.
        List<ColStatistics> colStats =
            StatsUtils.getColStatisticsFromExprMap(conf, parentStats, sop.getColumnExprMap(), sop.getSchema());
        stats.setColumnStats(colStats);
        // in case of select(*) the data size does not change
        if (!sop.getConf().isSelectStar() && !sop.getConf().isSelStarNoCompute()) {
          long dataSize = StatsUtils.getDataSizeFromColumnStats(stats.getNumRows(), colStats);
          stats.setDataSize(dataSize);
        }
        stats = applyRuntimeStats(aspCtx.getParseContext().getContext(), stats, sop);
        sop.setStatistics(stats);

        if (LOG.isDebugEnabled()) {
          LOG.debug("[0] STATS-" + sop.toString() + ": " + stats.extendedToString());
        }
      } else {
        if (parentStats != null) {
          stats = applyRuntimeStats(aspCtx.getParseContext().getContext(), stats, sop);
          sop.setStatistics(stats);

          if (LOG.isDebugEnabled()) {
            LOG.debug("[1] STATS-" + sop.toString() + ": " + parentStats.extendedToString());
          }
        }
      }
      return null;
    }

};