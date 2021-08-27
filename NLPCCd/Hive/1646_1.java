//,temp,sample_4629.java,2,13,temp,sample_4634.java,2,11
//,3
public class xxx {
public WMGetResourcePlanResponse get_resource_plan(WMGetResourcePlanRequest request) throws NoSuchObjectException, MetaException, TException {
try {
WMFullResourcePlan rp = getMS().getResourcePlan(request.getResourcePlanName());
WMGetResourcePlanResponse resp = new WMGetResourcePlanResponse();
resp.setResourcePlan(rp);
return resp;
} catch (MetaException e) {


log.info("exception while trying to retrieve resource plan");
}
}

};