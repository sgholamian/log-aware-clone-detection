//,temp,SparkCompiler.java,563,626,temp,TezCompiler.java,766,834
//,3
public class xxx {
  @Override
  protected void optimizeTaskPlan(List<Task<?>> rootTasks, ParseContext pCtx,
      Context ctx) throws SemanticException {
    PERF_LOGGER.perfLogBegin(CLASS_NAME, PerfLogger.SPARK_OPTIMIZE_TASK_TREE);
    PhysicalContext physicalCtx = new PhysicalContext(conf, pCtx, pCtx.getContext(), rootTasks,
       pCtx.getFetchTask());

    physicalCtx = new SplitSparkWorkResolver().resolve(physicalCtx);

    if (conf.getBoolVar(HiveConf.ConfVars.HIVESKEWJOIN)) {
      (new SparkSkewJoinResolver()).resolve(physicalCtx);
    } else {
      LOG.debug("Skipping runtime skew join optimization");
    }

    physicalCtx = new SparkMapJoinResolver().resolve(physicalCtx);

    if (conf.isSparkDPPAny()) {
      physicalCtx = new SparkDynamicPartitionPruningResolver().resolve(physicalCtx);
    }

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
      physicalCtx = new SparkCrossProductCheck().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping cross product analysis");
    }

    if (conf.getBoolVar(HiveConf.ConfVars.HIVE_VECTORIZATION_ENABLED)) {
      (new Vectorizer()).resolve(physicalCtx);
    } else {
      LOG.debug("Skipping vectorization");
    }

    if (!"none".equalsIgnoreCase(conf.getVar(HiveConf.ConfVars.HIVESTAGEIDREARRANGE))) {
      (new StageIDsRearranger()).resolve(physicalCtx);
    } else {
      LOG.debug("Skipping stage id rearranger");
    }

    if (conf.getBoolVar(HiveConf.ConfVars.HIVE_COMBINE_EQUIVALENT_WORK_OPTIMIZATION)) {
      new CombineEquivalentWorkResolver().resolve(physicalCtx);
    } else {
      LOG.debug("Skipping combine equivalent work optimization");
    }

    if (physicalCtx.getContext().getExplainAnalyze() != null) {
      new AnnotateRunTimeStatsOptimizer().resolve(physicalCtx);
    }

    PERF_LOGGER.perfLogEnd(CLASS_NAME, PerfLogger.SPARK_OPTIMIZE_TASK_TREE);
    return;
  }

};