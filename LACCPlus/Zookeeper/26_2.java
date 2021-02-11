//,temp,LeaderElectionSupportTest.java,167,185,temp,LeaderElectionSupportTest.java,123,143
//,3
public class xxx {
  @Test
  public void testNodes20() throws IOException, InterruptedException,
      KeeperException {

    int testIterations = 20;
    final CountDownLatch latch = new CountDownLatch(testIterations);
    final AtomicInteger failureCounter = new AtomicInteger();

    for (int i = 0; i < testIterations; i++) {
      runElectionSupportThread(latch, failureCounter);
    }

    Assert.assertEquals(0, failureCounter.get());

    if (!latch.await(10, TimeUnit.SECONDS)) {
      logger
          .info(
              "Waited for all threads to start, but timed out. We had {} failures.",
              failureCounter);
    }
  }

};