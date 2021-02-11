//,temp,LeaderElectionSupportTest.java,167,185,temp,LeaderElectionSupportTest.java,123,143
//,3
public class xxx {
  @Test
  public void testOfferShuffle() throws InterruptedException {
    int testIterations = 10;
    final CountDownLatch latch = new CountDownLatch(testIterations);
    final AtomicInteger failureCounter = new AtomicInteger();
    List<Thread> threads = new ArrayList<Thread>(testIterations);

    for (int i = 1; i <= testIterations; i++) {
      threads.add(runElectionSupportThread(latch, failureCounter,
          Math.min(i * 1200, 10000)));
    }

    if (!latch.await(60, TimeUnit.SECONDS)) {
      logger
          .info(
              "Waited for all threads to start, but timed out. We had {} failures.",
              failureCounter);
    }
  }

};