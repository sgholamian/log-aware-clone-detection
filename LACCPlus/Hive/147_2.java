//,temp,HMSHandler.java,10480,10494,temp,HMSHandler.java,10401,10416
//,3
public class xxx {
  @Override
  public ScheduledQueryPollResponse scheduled_query_poll(ScheduledQueryPollRequest request)
      throws MetaException, TException {
    startFunction("scheduled_query_poll");
    Exception ex = null;
    try {
      RawStore ms = getMS();
      return ms.scheduledQueryPoll(request);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("scheduled_query_poll", ex == null, ex);
    }
  }

};