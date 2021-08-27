//,temp,HMSHandler.java,9732,9743,temp,HMSHandler.java,9681,9693
//,3
public class xxx {
  @Override
  public WMGetResourcePlanResponse get_resource_plan(WMGetResourcePlanRequest request)
      throws NoSuchObjectException, MetaException, TException {
    try {
      WMFullResourcePlan rp = getMS().getResourcePlan(request.getResourcePlanName(), request.getNs());
      WMGetResourcePlanResponse resp = new WMGetResourcePlanResponse();
      resp.setResourcePlan(rp);
      return resp;
    } catch (MetaException e) {
      LOG.error("Exception while trying to retrieve resource plan", e);
      throw e;
    }
  }

};