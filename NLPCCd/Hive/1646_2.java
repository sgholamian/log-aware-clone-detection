//,temp,sample_4629.java,2,13,temp,sample_4634.java,2,11
//,3
public class xxx {
public WMDropResourcePlanResponse drop_resource_plan(WMDropResourcePlanRequest request) throws NoSuchObjectException, InvalidOperationException, MetaException, TException {
try {
getMS().dropResourcePlan(request.getResourcePlanName());
return new WMDropResourcePlanResponse();
} catch (MetaException e) {


log.info("exception while trying to drop resource plan");
}
}

};