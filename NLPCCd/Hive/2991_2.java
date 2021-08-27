//,temp,sample_2378.java,2,17,temp,sample_5412.java,2,17
//,3
public class xxx {
public void dummy_method(){
PerfLogger perfLogger = SessionState.getPerfLogger();
perfLogger.PerfLogBegin(this.getClass().getName(), PerfLogger.TEZ_COMPILER);
PhysicalContext physicalCtx = new PhysicalContext(conf, pCtx, pCtx.getContext(), rootTasks, pCtx.getFetchTask());
if (conf.getBoolVar(HiveConf.ConfVars.HIVENULLSCANOPTIMIZE)) {
physicalCtx = new NullScanOptimizer().resolve(physicalCtx);
} else {
}
if (conf.getBoolVar(HiveConf.ConfVars.HIVEMETADATAONLYQUERIES)) {
physicalCtx = new MetadataOnlyOptimizer().resolve(physicalCtx);
} else {


log.info("skipping metadata only query optimization");
}
}

};