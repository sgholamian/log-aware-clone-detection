//,temp,sample_5736.java,2,11,temp,sample_60.java,2,9
//,3
public class xxx {
protected void onChangeMonitoringContainerResource( ContainersMonitorEvent monitoringEvent, ContainerId containerId) {
ChangeMonitoringContainerResourceEvent changeEvent = (ChangeMonitoringContainerResourceEvent) monitoringEvent;
ProcessTreeInfo processTreeInfo = trackingContainers.get(containerId);
if (processTreeInfo == null) {
return;
}


log.info("changing resource monitoring for");
}

};