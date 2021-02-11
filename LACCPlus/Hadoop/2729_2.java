//,temp,TestNodeManagerResync.java,256,284,temp,TestNodeManagerResync.java,193,226
//,3
public class xxx {
  @SuppressWarnings("resource")
  @Test(timeout = 30000)
  public void testNMMultipleResyncEvent()
      throws IOException, InterruptedException {
    TestNodeManager1 nm = new TestNodeManager1(false);
    YarnConfiguration conf = createNMConfig();

    int resyncEventCount = 4;
    try {
      nm.init(conf);
      nm.start();
      Assert.assertEquals(1, nm.getNMRegistrationCount());
      for (int i = 0; i < resyncEventCount; i++) {
        nm.getNMDispatcher().getEventHandler().handle(resyncEvent);
      }

      DrainDispatcher dispatcher = (DrainDispatcher) nm.getNMDispatcher();
      dispatcher.await();
      LOG.info("NM dispatcher drained");

      // Wait for the resync thread to finish
      try {
        syncBarrier.await();
      } catch (BrokenBarrierException e) {
      }
      LOG.info("Barrier wait done for the resync thread");

      // Resync should only happen once
      Assert.assertEquals(2, nm.getNMRegistrationCount());
      Assert.assertFalse("NM shutdown called.", isNMShutdownCalled.get());
    } finally {
      nm.stop();
    }
  }

};