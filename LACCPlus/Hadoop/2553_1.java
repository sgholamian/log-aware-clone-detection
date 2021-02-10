//,temp,TestDataNodeLifeline.java,326,334,temp,TestDataNodeLifeline.java,306,313
//,3
public class xxx {
    @Override
    @SuppressWarnings("unchecked")
    public T answer(InvocationOnMock invocation)
        throws Throwable {
      T result = (T)invocation.callRealMethod();
      latch.countDown();
      LOG.info("Countdown, remaining latch count is {}.", latch.getCount());
      return result;
    }

};