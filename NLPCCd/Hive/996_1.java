//,temp,sample_5402.java,2,20,temp,sample_5401.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (!procCtx.conf.getBoolVar(ConfVars.TEZ_DYNAMIC_PARTITION_PRUNING)) {
return;
}
boolean cycleFree = false;
while (!cycleFree) {
cycleFree = true;
Set<Set<Operator<?>>> components = getComponents(procCtx);
for (Set<Operator<?>> component : components) {
if (LOG.isDebugEnabled()) {
for (Operator<?> co : component) {


log.info("operator");
}
}
}
}
}

};