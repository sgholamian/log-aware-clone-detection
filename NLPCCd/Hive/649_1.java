//,temp,sample_2370.java,2,15,temp,sample_5403.java,2,19
//,3
public class xxx {
private void runCycleAnalysisForPartitionPruning(OptimizeSparkProcContext procCtx) {
boolean cycleFree = false;
while (!cycleFree) {
cycleFree = true;
Set<Set<Operator<?>>> components = getComponents(procCtx);
for (Set<Operator<?>> component : components) {
if (LOG.isDebugEnabled()) {


log.info("component");
}
}
}
}

};