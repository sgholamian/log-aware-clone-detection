//,temp,TestNodeStatusUpdater.java,1467,1514,temp,TestNodeStatusUpdater.java,1107,1162
//,3
public class xxx {
  @Test
  public void testRMVersionLessThanMinimum() throws InterruptedException {
    final AtomicInteger numCleanups = new AtomicInteger(0);
    YarnConfiguration conf = createNMConfig();
    conf.set(YarnConfiguration.NM_RESOURCEMANAGER_MINIMUM_VERSION, "3.0.0");
    nm = new NodeManager() {
      @Override
      protected NodeStatusUpdater createNodeStatusUpdater(Context context,
                                                          Dispatcher dispatcher, NodeHealthCheckerService healthChecker) {
        MyNodeStatusUpdater myNodeStatusUpdater = new MyNodeStatusUpdater(
            context, dispatcher, healthChecker, metrics);
        MyResourceTracker2 myResourceTracker2 = new MyResourceTracker2();
        myResourceTracker2.heartBeatNodeAction = NodeAction.NORMAL;
        myResourceTracker2.rmVersion = "3.0.0";
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

    nm.init(conf);
    nm.start();

    // NM takes a while to reach the STARTED state.
    int waitCount = 0;
    while (nm.getServiceState() != STATE.STARTED && waitCount++ != 20) {
      LOG.info("Waiting for NM to stop..");
      Thread.sleep(1000);
    }
    Assert.assertTrue(nm.getServiceState() == STATE.STARTED);
    nm.stop();
  }

};