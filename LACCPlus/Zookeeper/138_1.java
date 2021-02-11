//,temp,LeaderElectionSupportTest.java,145,165,temp,LeaderElectionSupportTest.java,101,121
//,2
public class xxx {
  @Test
  public void testNodes100() throws IOException, InterruptedException,
      KeeperException {

    int testIterations = 100;
    final CountDownLatch latch = new CountDownLatch(testIterations);
    final AtomicInteger failureCounter = new AtomicInteger();

    for (int i = 0; i < testIterations; i++) {
      runElectionSupportThread(latch, failureCounter);
    }

    Assert.assertEquals(0, failureCounter.get());

    if (!latch.await(20, TimeUnit.SECONDS)) {
      logger
          .info(
              "Waited for all threads to start, but timed out. We had {} failures.",
              failureCounter);
    }
  }

};