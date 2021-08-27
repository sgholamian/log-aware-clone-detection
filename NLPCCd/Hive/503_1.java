//,temp,sample_4637.java,2,11,temp,sample_4638.java,2,13
//,3
public class xxx {
public WMDropTriggerResponse drop_wm_trigger(WMDropTriggerRequest request) throws NoSuchObjectException, InvalidOperationException, MetaException, TException {
try {
getMS().dropWMTrigger(request.getResourcePlanName(), request.getTriggerName());
return new WMDropTriggerResponse();
} catch (MetaException e) {


log.info("exception while trying to drop trigger");
}
}

};