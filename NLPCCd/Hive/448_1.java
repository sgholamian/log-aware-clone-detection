//,temp,sample_4633.java,2,10,temp,sample_4641.java,2,11
//,3
public class xxx {
public WMValidateResourcePlanResponse validate_resource_plan(WMValidateResourcePlanRequest request) throws NoSuchObjectException, MetaException, TException {
try {
return getMS().validateResourcePlan(request.getResourcePlanName());
} catch (MetaException e) {


log.info("exception while trying to validate resource plan");
}
}

};