//,temp,TestContainerLauncherImpl.java,277,326,temp,TestContainerLauncherImpl.java,216,275
//,3
public class xxx {
  @Test(timeout = 5000)
  public void testOutOfOrder() throws Exception {
    LOG.info("STARTING testOutOfOrder");
    AppContext mockContext = mock(AppContext.class);
    @SuppressWarnings("rawtypes")
    EventHandler mockEventHandler = mock(EventHandler.class);
    when(mockContext.getEventHandler()).thenReturn(mockEventHandler);

    ContainerManagementProtocolClient mockCM =
        mock(ContainerManagementProtocolClient.class);
    ContainerLauncherImplUnderTest ut =
        new ContainerLauncherImplUnderTest(mockContext, mockCM);
    
    Configuration conf = new Configuration();
    ut.init(conf);
    ut.start();
    try {
      ContainerId contId = makeContainerId(0l, 0, 0, 1);
      TaskAttemptId taskAttemptId = makeTaskAttemptId(0l, 0, 0, TaskType.MAP, 0);
      String cmAddress = "127.0.0.1:8000";
      StartContainersResponse startResp =
        recordFactory.newRecordInstance(StartContainersResponse.class);
      startResp.setAllServicesMetaData(serviceResponse);

      LOG.info("inserting cleanup event");
      ContainerLauncherEvent mockCleanupEvent = 
        mock(ContainerLauncherEvent.class);
      when(mockCleanupEvent.getType())
        .thenReturn(EventType.CONTAINER_REMOTE_CLEANUP);
      when(mockCleanupEvent.getContainerID())
        .thenReturn(contId);
      when(mockCleanupEvent.getTaskAttemptID()).thenReturn(taskAttemptId);
      when(mockCleanupEvent.getContainerMgrAddress()).thenReturn(cmAddress);
      ut.handle(mockCleanupEvent);
      
      ut.waitForPoolToIdle();
      
      verify(mockCM, never()).stopContainers(any(StopContainersRequest.class));

      LOG.info("inserting launch event");
      ContainerRemoteLaunchEvent mockLaunchEvent = 
        mock(ContainerRemoteLaunchEvent.class);
      when(mockLaunchEvent.getType())
        .thenReturn(EventType.CONTAINER_REMOTE_LAUNCH);
      when(mockLaunchEvent.getContainerID())
        .thenReturn(contId);
      when(mockLaunchEvent.getTaskAttemptID()).thenReturn(taskAttemptId);
      when(mockLaunchEvent.getContainerMgrAddress()).thenReturn(cmAddress);
      when(mockCM.startContainers(any(StartContainersRequest.class))).thenReturn(startResp);
      when(mockLaunchEvent.getContainerToken()).thenReturn(
          createNewContainerToken(contId, cmAddress));
      ut.handle(mockLaunchEvent);
      
      ut.waitForPoolToIdle();
      
      verify(mockCM, never()).startContainers(any(StartContainersRequest.class));
    } finally {
      ut.stop();
    }
  }

};