//,temp,sample_8253.java,2,13,temp,sample_9205.java,2,14
//,3
public class xxx {
protected void removePendingChangeRequests( List<UpdatedContainer> changedContainers) {
for (UpdatedContainer changedContainer : changedContainers) {
ContainerId containerId = changedContainer.getContainer().getId();
if (pendingChange.get(containerId) == null) {
continue;
}
if (LOG.isDebugEnabled()) {


log.info("rm has confirmed changed resource allocation for container current resource allocation remove pending change request");
}
}
}

};