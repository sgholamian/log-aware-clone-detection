//,temp,TestLocalContainerLauncher.java,99,178,temp,TestContainerLauncherImpl.java,328,405
//,3
public class xxx {
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Test(timeout = 5000)
  public void testContainerCleaned() throws Exception {
    LOG.info("STARTING testContainerCleaned");
    
    CyclicBarrier startLaunchBarrier = new CyclicBarrier(2);
    CyclicBarrier completeLaunchBarrier = new CyclicBarrier(2);

    AppContext mockContext = mock(AppContext.class);
    
    EventHandler mockEventHandler = mock(EventHandler.class);
    when(mockContext.getEventHandler()).thenReturn(mockEventHandler);

    ContainerManagementProtocolClient mockCM =
        new ContainerManagerForTest(startLaunchBarrier, completeLaunchBarrier);
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
      
     
      LOG.info("inserting launch event");
      ContainerRemoteLaunchEvent mockLaunchEvent = 
        mock(ContainerRemoteLaunchEvent.class);
      when(mockLaunchEvent.getType())
        .thenReturn(EventType.CONTAINER_REMOTE_LAUNCH);
      when(mockLaunchEvent.getContainerID())
        .thenReturn(contId);
      when(mockLaunchEvent.getTaskAttemptID()).thenReturn(taskAttemptId);
      when(mockLaunchEvent.getContainerMgrAddress()).thenReturn(cmAddress);
      when(mockLaunchEvent.getContainerToken()).thenReturn(
          createNewContainerToken(contId, cmAddress));
      ut.handle(mockLaunchEvent);
      
      startLaunchBarrier.await();
      
           
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

      completeLaunchBarrier.await();
     
      ut.waitForPoolToIdle();
      
      ArgumentCaptor<Event> arg = ArgumentCaptor.forClass(Event.class);
      verify(mockEventHandler, atLeast(2)).handle(arg.capture());
      boolean containerCleaned = false;
      
      for (int i =0; i < arg.getAllValues().size(); i++) {
        LOG.info(arg.getAllValues().get(i).toString());
        Event currentEvent = arg.getAllValues().get(i);
        if (currentEvent.getType() == TaskAttemptEventType.TA_CONTAINER_CLEANED) {
          containerCleaned = true;
        }
      }
      assert(containerCleaned);
      
    } finally {
      ut.stop();
    }
  }

};