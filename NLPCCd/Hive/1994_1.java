//,temp,sample_2194.java,2,9,temp,sample_2202.java,2,9
//,2
public class xxx {
public void registerContainerEnd(ContainerId containerId, ContainerEndReason endReason, String diagnostics) {
super.registerContainerEnd(containerId, endReason, diagnostics);
if (endReason == ContainerEndReason.INTERNAL_PREEMPTION) {


log.info("processing containerend for container caused by internal preemption");
}
}

};