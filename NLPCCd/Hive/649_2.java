//,temp,sample_2370.java,2,15,temp,sample_5403.java,2,19
//,3
public class xxx {
public void dummy_method(){
boolean cycleFree = false;
while (!cycleFree) {
cycleFree = true;
Set<Set<Operator<?>>> components = getComponents(procCtx);
for (Set<Operator<?>> component : components) {
if (LOG.isDebugEnabled()) {
for (Operator<?> co : component) {
}
}
if (component.size() != 1) {


log.info("found cycle in operator plan");
}
}
}
}

};