//,temp,sample_8231.java,2,10,temp,sample_3014.java,2,8
//,3
public class xxx {
public void getContainerStatusAsync(ContainerId containerId, NodeId nodeId) {
try {
events.put(new ContainerEvent(containerId, nodeId, null, ContainerEventType.QUERY_CONTAINER));
} catch (InterruptedException e) {


log.info("exception when scheduling the event of querying the status of container");
}
}

};