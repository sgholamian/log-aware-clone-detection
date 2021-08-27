//,temp,HMSHandler.java,9804,9818,temp,HMSHandler.java,9756,9766
//,3
public class xxx {
  @Override
  public WMGetTriggersForResourePlanResponse get_triggers_for_resourceplan(
      WMGetTriggersForResourePlanRequest request)
      throws NoSuchObjectException, MetaException, TException {
    try {
      List<WMTrigger> triggers =
          getMS().getTriggersForResourcePlan(request.getResourcePlanName(), request.getNs());
      WMGetTriggersForResourePlanResponse response = new WMGetTriggersForResourePlanResponse();
      response.setTriggers(triggers);
      return response;
    } catch (MetaException e) {
      LOG.error("Exception while trying to retrieve triggers plans", e);
      throw e;
    }
  }

};