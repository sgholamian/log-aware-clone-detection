//,temp,HMSHandler.java,10465,10478,temp,HMSHandler.java,10418,10432
//,3
public class xxx {
  @Override
  public void scheduled_query_maintenance(ScheduledQueryMaintenanceRequest request) throws MetaException, TException {
    startFunction("scheduled_query_poll");
    Exception ex = null;
    try {
      RawStore ms = getMS();
      ms.scheduledQueryMaintenance(request);
    } catch (Exception e) {
      LOG.error("Caught exception", e);
      ex = e;
      throw e;
    } finally {
      endFunction("scheduled_query_poll", ex == null, ex);
    }
  }

};