//,temp,sample_7621.java,2,17,temp,sample_7620.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (privateIp != null) {
if (!NetUtils.isValidIp(privateIp.toString())) {
throw new InvalidParameterValueException("Invalid vm ip address");
}
}
try {
PortForwardingRule result = _rulesService.createPortForwardingRule(this, virtualMachineId, privateIp, getOpenFirewall(), isDisplay());
setEntityId(result.getId());
setEntityUuid(result.getUuid());
} catch (NetworkRuleConflictException ex) {


log.info("network rule conflict");
}
}

};