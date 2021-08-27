//,temp,HMSHandler.java,9804,9818,temp,HMSHandler.java,9756,9766
//,3
public class xxx {
  @Override
  public WMDropResourcePlanResponse drop_resource_plan(WMDropResourcePlanRequest request)
      throws NoSuchObjectException, InvalidOperationException, MetaException, TException {
    try {
      getMS().dropResourcePlan(request.getResourcePlanName(), request.getNs());
      return new WMDropResourcePlanResponse();
    } catch (MetaException e) {
      LOG.error("Exception while trying to drop resource plan", e);
      throw e;
    }
  }

};