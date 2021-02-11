//,temp,sample_3328.java,2,16,temp,sample_5767.java,2,16
//,2
public class xxx {
public void execute() {
Vpc vpc = null;
try {
if (isStart()) {
_vpcService.startVpc(getEntityId(), true);
} else {
s_logger.debug("Not starting VPC as " + ApiConstants.START + "=false was passed to the API");
}
vpc = _entityMgr.findById(Vpc.class, getEntityId());
} catch (ResourceUnavailableException ex) {


log.info("exception");
}
}

};