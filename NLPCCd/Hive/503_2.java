//,temp,sample_4637.java,2,11,temp,sample_4638.java,2,13
//,3
public class xxx {
public WMGetTriggersForResourePlanResponse get_triggers_for_resourceplan( WMGetTriggersForResourePlanRequest request) throws NoSuchObjectException, MetaException, TException {
try {
List<WMTrigger> triggers = getMS().getTriggersForResourcePlan(request.getResourcePlanName());
WMGetTriggersForResourePlanResponse response = new WMGetTriggersForResourePlanResponse();
response.setTriggers(triggers);
return response;
} catch (MetaException e) {


log.info("exception while trying to retrieve triggers plans");
}
}

};