//,temp,TestNodeStatusUpdater.java,1646,1701,temp,TestNodeStatusUpdater.java,1108,1166
//,3
public class xxx {
  @Test
  public void testSignalContainerToContainerManager() throws Exception {
    nm = new NodeManager() {
      @Override
      protected NodeStatusUpdater createNodeStatusUpdater(Context context,
          Dispatcher dispatcher, NodeHealthCheckerService healthChecker) {
        return new MyNodeStatusUpdater(
            context, dispatcher, healthChecker, metrics, true);
      }

      @Override
      protected ContainerManagerImpl createContainerManager(Context context,
          ContainerExecutor exec, DeletionService del,
          NodeStatusUpdater nodeStatusUpdater,
          ApplicationACLsManager aclsManager,
          LocalDirsHandlerService diskhandler) {
        return new MyContainerManager(context, exec, del, nodeStatusUpdater,
            metrics, diskhandler);
      }
    };

    YarnConfiguration conf = createNMConfig();
    nm.init(conf);
    nm.start();

    System.out.println(" ----- thread already started.."
        + nm.getServiceState());

    int waitCount = 0;
    while (nm.getServiceState() == STATE.INITED && waitCount++ != 20) {
      LOG.info("Waiting for NM to start..");
      if (nmStartError != null) {
        LOG.error("Error during startup. ", nmStartError);
        Assert.fail(nmStartError.getCause().getMessage());
      }
      Thread.sleep(1000);
    }
    if (nm.getServiceState() != STATE.STARTED) {
      // NM could have failed.
      Assert.fail("NodeManager failed to start");
    }

    waitCount = 0;
    while (heartBeatID <= 3 && waitCount++ != 20) {
      Thread.sleep(500);
    }
    Assert.assertFalse(heartBeatID <= 3);
    Assert.assertEquals("Number of registered NMs is wrong!!", 1,
        this.registeredNodes.size());

    MyContainerManager containerManager =
        (MyContainerManager)nm.getContainerManager();
    Assert.assertTrue(containerManager.signaled);

    nm.stop();
  }

};