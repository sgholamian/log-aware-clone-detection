//,temp,HMSHandler.java,9745,9754,temp,HMSHandler.java,9695,9706
//,3
public class xxx {
  @Override
  public WMGetAllResourcePlanResponse get_all_resource_plans(WMGetAllResourcePlanRequest request)
      throws MetaException, TException {
    try {
      WMGetAllResourcePlanResponse resp = new WMGetAllResourcePlanResponse();
      resp.setResourcePlans(getMS().getAllResourcePlans(request.getNs()));
      return resp;
    } catch (MetaException e) {
      LOG.error("Exception while trying to retrieve resource plans", e);
      throw e;
    }
  }

};