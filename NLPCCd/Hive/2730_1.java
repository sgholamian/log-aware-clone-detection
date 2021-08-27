//,temp,sample_5411.java,2,13,temp,sample_2376.java,2,13
//,3
public class xxx {
protected void optimizeTaskPlan(List<Task<? extends Serializable>> rootTasks, ParseContext pCtx, Context ctx) throws SemanticException {
PerfLogger perfLogger = SessionState.getPerfLogger();
perfLogger.PerfLogBegin(this.getClass().getName(), PerfLogger.TEZ_COMPILER);
PhysicalContext physicalCtx = new PhysicalContext(conf, pCtx, pCtx.getContext(), rootTasks, pCtx.getFetchTask());
if (conf.getBoolVar(HiveConf.ConfVars.HIVENULLSCANOPTIMIZE)) {
physicalCtx = new NullScanOptimizer().resolve(physicalCtx);
} else {


log.info("skipping null scan query optimization");
}
}

};