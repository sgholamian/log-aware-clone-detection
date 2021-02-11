//,temp,sample_568.java,2,16,temp,sample_565.java,2,16
//,3
public class xxx {
public void dummy_method(){
ContainerRemoteLaunchEvent mockLaunchEvent = mock(ContainerRemoteLaunchEvent.class);
when(mockLaunchEvent.getType()) .thenReturn(EventType.CONTAINER_REMOTE_LAUNCH);
when(mockLaunchEvent.getContainerID()) .thenReturn(contId);
when(mockLaunchEvent.getTaskAttemptID()).thenReturn(taskAttemptId);
when(mockLaunchEvent.getContainerMgrAddress()).thenReturn(cmAddress);
when(mockCM.startContainers(any(StartContainersRequest.class))).thenReturn(startResp);
when(mockLaunchEvent.getContainerToken()).thenReturn( createNewContainerToken(contId, cmAddress));
ut.handle(mockLaunchEvent);
ut.waitForPoolToIdle();
verify(mockCM).startContainers(any(StartContainersRequest.class));


log.info("inserting cleanup event");
}

};