//,temp,sample_568.java,2,16,temp,sample_565.java,2,16
//,3
public class xxx {
public void dummy_method(){
StartContainersResponse startResp = recordFactory.newRecordInstance(StartContainersResponse.class);
startResp.setAllServicesMetaData(serviceResponse);
ContainerLauncherEvent mockCleanupEvent = mock(ContainerLauncherEvent.class);
when(mockCleanupEvent.getType()) .thenReturn(EventType.CONTAINER_REMOTE_CLEANUP);
when(mockCleanupEvent.getContainerID()) .thenReturn(contId);
when(mockCleanupEvent.getTaskAttemptID()).thenReturn(taskAttemptId);
when(mockCleanupEvent.getContainerMgrAddress()).thenReturn(cmAddress);
ut.handle(mockCleanupEvent);
ut.waitForPoolToIdle();
verify(mockCM, never()).stopContainers(any(StopContainersRequest.class));


log.info("inserting launch event");
}

};