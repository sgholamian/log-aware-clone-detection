//,temp,GenericTestUtils.java,152,165,temp,TestHRegion.java,6089,6099
//,3
public class xxx {
    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
      LOG.info("DelayAnswer firing fireLatch");
      fireCounter.getAndIncrement();
      fireLatch.countDown();
      try {
        LOG.info("DelayAnswer waiting on waitLatch");
        waitLatch.await();
        LOG.info("DelayAnswer delay complete");
      } catch (InterruptedException ie) {
        throw new IOException("Interrupted waiting on latch", ie);
      }
      return passThrough(invocation);
    }

};