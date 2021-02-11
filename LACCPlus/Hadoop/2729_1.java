//,temp,TestNodeManagerResync.java,256,284,temp,TestNodeManagerResync.java,193,226
//,3
public class xxx {
  @SuppressWarnings("resource")
  @Test(timeout=60000)
  public void testContainerResourceIncreaseIsSynchronizedWithRMResync()
      throws IOException, InterruptedException, YarnException {
    NodeManager nm = new TestNodeManager4();
    YarnConfiguration conf = createNMConfig();
    conf.setBoolean(
        YarnConfiguration.RM_WORK_PRESERVING_RECOVERY_ENABLED, true);
    try {
      nm.init(conf);
      nm.start();
      // Start a container and make sure it is in RUNNING state
      ((TestNodeManager4) nm).startContainer();
      // Simulate a container resource increase in a separate thread
      ((TestNodeManager4) nm).updateContainerResource();
      // Simulate RM restart by sending a RESYNC event
      LOG.info("Sending out RESYNC event");
      nm.getNMDispatcher().getEventHandler()
          .handle(new NodeManagerEvent(NodeManagerEventType.RESYNC));
      try {
        syncBarrier.await();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
      Assert.assertFalse(assertionFailedInThread.get());
    } finally {
      nm.stop();
    }
  }

};