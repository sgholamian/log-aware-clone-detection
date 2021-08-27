//,temp,sample_5284.java,2,18,temp,sample_5285.java,2,17
//,3
public class xxx {
public void dummy_method(){
OptimizeSparkProcContext context = (OptimizeSparkProcContext) procContext;
ReduceSinkOperator sink = (ReduceSinkOperator) nd;
ReduceSinkDesc desc = sink.getConf();
Set<ReduceSinkOperator> parentSinks = null;
int maxReducers = context.getConf().getIntVar(HiveConf.ConfVars.MAXREDUCERS);
int constantReducers = context.getConf().getIntVar(HiveConf.ConfVars.HADOOPNUMREDUCERS);
if (!useOpStats) {
parentSinks = OperatorUtils.findOperatorsUpstream(sink, ReduceSinkOperator.class);
parentSinks.remove(sink);
if (!context.getVisitedReduceSinks().containsAll(parentSinks)) {


log.info("skipping sink for now as we haven t seen all its parents");
}
}
}

};