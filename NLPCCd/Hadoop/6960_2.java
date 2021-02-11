//,temp,sample_6320.java,2,14,temp,sample_5735.java,2,10
//,3
public class xxx {
protected void onChangeMonitoringContainerResource( ContainersMonitorEvent monitoringEvent, ContainerId containerId) {
ChangeMonitoringContainerResourceEvent changeEvent = (ChangeMonitoringContainerResourceEvent) monitoringEvent;
ProcessTreeInfo processTreeInfo = trackingContainers.get(containerId);
if (processTreeInfo == null) {


log.info("failed to track container it may have already completed");
}
}

};