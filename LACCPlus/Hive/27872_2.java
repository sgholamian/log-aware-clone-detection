//,temp,SparkCompiler.java,563,626,temp,TezCompiler.java,766,834
//,3
public class xxx {
  @Override
  protected void optimizeTaskPlan(List<Task<?>> rootTasks, ParseContext pCtx,
      Context ctx) throws SemanticException {
    PerfLogger perfLogger = SessionState.getPerfLogger();
    perfLogger.perfLogBegin(this.getClass().getName(), PerfLogger.TEZ_COMPILER);
    PhysicalContext physicalCtx = new PhysicalContext(conf, pCtx, pCtx.getContext(), rootTasks,
       pCtx.getFetchTask());

    if (conf.getBoolVar(HiveConf.ConfVars.HIVENULLSCANOPTIMIZE)) {
      physicalCtx = new NullScanOptimizer().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping null scan query optimization");
    }

    if (conf.getBoolVar(HiveConf.ConfVars.HIVEMETADATAONLYQUERIES)) {
      physicalCtx = new MetadataOnlyOptimizer().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping metadata only query optimization");
    }

    if (conf.getBoolVar(HiveConf.ConfVars.HIVE_CHECK_CROSS_PRODUCT)) {
      physicalCtx = new CrossProductHandler().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping cross product analysis");
    }

    if ("llap".equalsIgnoreCase(conf.getVar(HiveConf.ConfVars.HIVE_EXECUTION_MODE))) {
      physicalCtx = new LlapPreVectorizationPass().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping llap pre-vectorization pass");
    }

    if (conf.getBoolVar(HiveConf.ConfVars.HIVE_VECTORIZATION_ENABLED)) {
      physicalCtx = new Vectorizer().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping vectorization");
    }

    if (!"none".equalsIgnoreCase(conf.getVar(HiveConf.ConfVars.HIVESTAGEIDREARRANGE))) {
      physicalCtx = new StageIDsRearranger().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping stage id rearranger");
    }

    if ((conf.getBoolVar(HiveConf.ConfVars.HIVE_TEZ_ENABLE_MEMORY_MANAGER))
        && (conf.getBoolVar(HiveConf.ConfVars.HIVEUSEHYBRIDGRACEHASHJOIN))) {
      physicalCtx = new MemoryDecider().resolve(physicalCtx);
    }

    if ("llap".equalsIgnoreCase(conf.getVar(HiveConf.ConfVars.HIVE_EXECUTION_MODE))) {
      LlapClusterStateForCompile llapInfo = LlapClusterStateForCompile.getClusterInfo(conf);
      physicalCtx = new LlapDecider(llapInfo).resolve(physicalCtx);
    } else {
      LOG.debug("Skipping llap decider");
    }

    //  This optimizer will serialize all filters that made it to the
    //  table scan operator to avoid having to do it multiple times on
    //  the backend. If you have a physical optimization that changes
    //  table scans or filters, you have to invoke it before this one.
    physicalCtx = new SerializeFilter().resolve(physicalCtx);

    if (physicalCtx.getContext().getExplainAnalyze() != null) {
      new AnnotateRunTimeStatsOptimizer().resolve(physicalCtx);
    }

    perfLogger.perfLogEnd(this.getClass().getName(), PerfLogger.TEZ_COMPILER, "optimizeTaskPlan");
    return;
  }

};