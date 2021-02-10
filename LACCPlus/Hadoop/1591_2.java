//,temp,TestNodeStatusUpdater.java,1467,1514,temp,TestNodeStatusUpdater.java,1107,1162
//,3
public class xxx {
  @Test
  public void testStopReentrant() throws Exception {
    final AtomicInteger numCleanups = new AtomicInteger(0);
    nm = new NodeManager() {
      @Override
      protected NodeStatusUpdater createNodeStatusUpdater(Context context,
          Dispatcher dispatcher, NodeHealthCheckerService healthChecker) {
        MyNodeStatusUpdater myNodeStatusUpdater = new MyNodeStatusUpdater(
            context, dispatcher, healthChecker, metrics);
        MyResourceTracker2 myResourceTracker2 = new MyResourceTracker2();
        myResourceTracker2.heartBeatNodeAction = NodeAction.SHUTDOWN;
        myNodeStatusUpdater.resourceTracker = myResourceTracker2;
        return myNodeStatusUpdater;
      }

      @Override
      protected ContainerManagerImpl createContainerManager(Context context,
          ContainerExecutor exec, DeletionService del,
          NodeStatusUpdater nodeStatusUpdater,
          ApplicationACLsManager aclsManager,
          LocalDirsHandlerService dirsHandler) {
        return new ContainerManagerImpl(context, exec, del, nodeStatusUpdater,
            metrics, aclsManager, dirsHandler) {

          @Override
          public void cleanUpApplicationsOnNMShutDown() {
            super.cleanUpApplicationsOnNMShutDown();
            numCleanups.incrementAndGet();
          }
        };
      }
    };

    YarnConfiguration conf = createNMConfig();
    nm.init(conf);
    nm.start();

    int waitCount = 0;
    while (heartBeatID < 1 && waitCount++ != 200) {
      Thread.sleep(500);
    }
    Assert.assertFalse(heartBeatID < 1);

    // Meanwhile call stop directly as the shutdown hook would
    nm.stop();

    // NM takes a while to reach the STOPPED state.
    waitCount = 0;
    while (nm.getServiceState() != STATE.STOPPED && waitCount++ != 20) {
      LOG.info("Waiting for NM to stop..");
      Thread.sleep(1000);
    }

    Assert.assertEquals(STATE.STOPPED, nm.getServiceState());
    Assert.assertEquals(numCleanups.get(), 1);
  }

};