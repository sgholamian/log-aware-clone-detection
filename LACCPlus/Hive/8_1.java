//,temp,HMSHandler.java,9732,9743,temp,HMSHandler.java,9681,9693
//,3
public class xxx {
  @Override
  public WMGetActiveResourcePlanResponse get_active_resource_plan(
      WMGetActiveResourcePlanRequest request) throws MetaException, TException {
    try {
      WMGetActiveResourcePlanResponse response = new WMGetActiveResourcePlanResponse();
      response.setResourcePlan(getMS().getActiveResourcePlan(request.getNs()));
      return response;
    } catch (MetaException e) {
      LOG.error("Exception while trying to get active resource plan", e);
      throw e;
    }
  }

};