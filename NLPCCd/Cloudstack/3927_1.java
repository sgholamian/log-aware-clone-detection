//,temp,sample_5775.java,2,17,temp,sample_5776.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (getVpcId() != null) {
throw new  InvalidParameterValueException("Unable to create firewall rule for the network id=" + networkId + " as firewall egress rule can be created only for non vpc networks.");
}
try {
FirewallRule result = _firewallService.createEgressFirewallRule(this);
if (result != null) {
setEntityId(result.getId());
setEntityUuid(result.getUuid());
}
} catch (NetworkRuleConflictException ex) {


log.info("network rule conflict");
}
}

};