//,temp,sample_2021.java,2,16,temp,sample_2022.java,2,16
//,2
public class xxx {
public void dummy_method(){
try {
lSwitchPort = new LogicalSwitchPort();
lSwitchPort.setAdminStatusEnabled(true);
lSwitchPort.setDisplayName(niciraNvpResource.truncate(networkId + "-l2Gateway-port", NAME_MAX_LEN));
lSwitchPort.setTags(tags);
lSwitchPort = niciraNvpApi.createLogicalSwitchPort(logicalSwitchUuid, lSwitchPort);
}
catch (NiciraNvpApiException e){
return handleException(e, command, niciraNvpResource);
}


log.info("attaching logical switch port on vlan using");
}

};