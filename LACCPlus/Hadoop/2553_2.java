//,temp,TestDataNodeLifeline.java,326,334,temp,TestDataNodeLifeline.java,306,313
//,3
public class xxx {
    @Override
    @SuppressWarnings("unchecked")
    public T answer(InvocationOnMock invocation)
        throws Throwable {
      LOG.info("Awaiting, remaining latch count is {}.", latch.getCount());
      latch.await();
      return (T)invocation.callRealMethod();
    }

};