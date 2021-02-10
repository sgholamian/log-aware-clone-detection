//,temp,TestNodeStatusUpdater.java,1646,1701,temp,TestNodeStatusUpdater.java,1108,1166
//,3
public class xxx {
  @Test
  public void testNMRegistration() throws InterruptedException, IOException {
    nm = new NodeManager() {
      @Override
      protected NodeStatusUpdater createNodeStatusUpdater(Context context,
          Dispatcher dispatcher, NodeHealthCheckerService healthChecker) {
        return new MyNodeStatusUpdater(context, dispatcher, healthChecker,
                                       metrics);
      }
    };

    YarnConfiguration conf = createNMConfig();
    nm.init(conf);

    // verify that the last service is the nodeStatusUpdater (ie registration
    // with RM)
    Object[] services  = nm.getServices().toArray();
    Object lastService = services[services.length-1];
    Assert.assertTrue("last service is NOT the node status updater",
        lastService instanceof NodeStatusUpdater);

    new Thread() {
      public void run() {
        try {
          nm.start();
        } catch (Throwable e) {
          TestNodeStatusUpdater.this.nmStartError = e;
          throw new YarnRuntimeException(e);
        }
      }
    }.start();

    System.out.println(" ----- thread already started.."
        + nm.getServiceState());

    int waitCount = 0;
    while (nm.getServiceState() == STATE.INITED && waitCount++ != 50) {
      LOG.info("Waiting for NM to start..");
      if (nmStartError != null) {
        LOG.error("Error during startup. ", nmStartError);
        Assert.fail(nmStartError.getCause().getMessage());
      }
      Thread.sleep(2000);
    }
    if (nm.getServiceState() != STATE.STARTED) {
      // NM could have failed.
      Assert.fail("NodeManager failed to start");
    }

    waitCount = 0;
    while (heartBeatID <= 3 && waitCount++ != 200) {
      Thread.sleep(1000);
    }
    Assert.assertFalse(heartBeatID <= 3);
    Assert.assertEquals("Number of registered NMs is wrong!!", 1,
        this.registeredNodes.size());

    nm.stop();
  }

};