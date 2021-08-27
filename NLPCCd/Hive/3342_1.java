//,temp,sample_4636.java,2,11,temp,sample_4635.java,2,11
//,2
public class xxx {
public WMAlterTriggerResponse alter_wm_trigger(WMAlterTriggerRequest request) throws NoSuchObjectException, InvalidObjectException, MetaException, TException {
try {
getMS().alterWMTrigger(request.getTrigger());
return new WMAlterTriggerResponse();
} catch (MetaException e) {


log.info("exception while trying to alter trigger");
}
}

};