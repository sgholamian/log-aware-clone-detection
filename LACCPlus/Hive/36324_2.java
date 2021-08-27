//,temp,HMSHandler.java,9846,9856,temp,HMSHandler.java,9792,9802
//,2
public class xxx {
  @Override
  public WMDropTriggerResponse drop_wm_trigger(WMDropTriggerRequest request)
      throws NoSuchObjectException, InvalidOperationException, MetaException, TException {
    try {
      getMS().dropWMTrigger(request.getResourcePlanName(), request.getTriggerName(), request.getNs());
      return new WMDropTriggerResponse();
    } catch (MetaException e) {
      LOG.error("Exception while trying to drop trigger.", e);
      throw e;
    }
  }

};