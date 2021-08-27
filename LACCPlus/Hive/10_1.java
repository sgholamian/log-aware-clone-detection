//,temp,HMSHandler.java,9745,9754,temp,HMSHandler.java,9695,9706
//,3
public class xxx {
  @Override
  public WMValidateResourcePlanResponse validate_resource_plan(WMValidateResourcePlanRequest request)
      throws NoSuchObjectException, MetaException, TException {
    try {
      return getMS().validateResourcePlan(request.getResourcePlanName(), request.getNs());
    } catch (MetaException e) {
      LOG.error("Exception while trying to validate resource plan", e);
      throw e;
    }
  }

};