//,temp,sample_5411.java,2,13,temp,sample_2376.java,2,13
//,3
public class xxx {
protected void optimizeTaskPlan(List<Task<? extends Serializable>> rootTasks, ParseContext pCtx, Context ctx) throws SemanticException {
PERF_LOGGER.PerfLogBegin(CLASS_NAME, PerfLogger.SPARK_OPTIMIZE_TASK_TREE);
PhysicalContext physicalCtx = new PhysicalContext(conf, pCtx, pCtx.getContext(), rootTasks, pCtx.getFetchTask());
physicalCtx = new SplitSparkWorkResolver().resolve(physicalCtx);
if (conf.getBoolVar(HiveConf.ConfVars.HIVESKEWJOIN)) {
(new SparkSkewJoinResolver()).resolve(physicalCtx);
} else {


log.info("skipping runtime skew join optimization");
}
}

};