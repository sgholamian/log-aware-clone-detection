//,temp,HMSHandler.java,10450,10463,temp,HMSHandler.java,10434,10448
//,3
public class xxx {
  @Override
  public ScheduledQuery get_scheduled_query(ScheduledQueryKey scheduleKey) throws TException {
    startFunction("get_scheduled_query");
    Exception ex = null;
    try {
      return getMS().getScheduledQuery(scheduleKey);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("get_scheduled_query", ex == null, ex);
    }
  }

};