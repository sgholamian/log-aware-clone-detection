//,temp,sample_4636.java,2,11,temp,sample_4635.java,2,11
//,2
public class xxx {
public WMCreateTriggerResponse create_wm_trigger(WMCreateTriggerRequest request) throws AlreadyExistsException, InvalidObjectException, MetaException, TException {
try {
getMS().createWMTrigger(request.getTrigger());
return new WMCreateTriggerResponse();
} catch (MetaException e) {


log.info("exception while trying to create trigger");
}
}

};