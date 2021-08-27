//,temp,HMSHandler.java,10450,10463,temp,HMSHandler.java,10434,10448
//,3
public class xxx {
  @Override
  public void scheduled_query_progress(ScheduledQueryProgressInfo info) throws MetaException, TException {
    startFunction("scheduled_query_poll");
    Exception ex = null;
    try {
      RawStore ms = getMS();
      ms.scheduledQueryProgress(info);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("scheduled_query_poll", ex == null, ex);
    }
  }

};