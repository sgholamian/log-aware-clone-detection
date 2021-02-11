//,temp,sample_7501.java,2,13,temp,sample_7502.java,2,13
//,2
public class xxx {
public Answer execute(ConfigureSharedNetworkUuidCommand command, NiciraNvpResource niciraNvpResource) {
final String logicalRouterUuid = command.getLogicalRouterUuid();
final String logicalSwitchUuid = command.getLogicalSwitchUuid();
final String portIpAddress = command.getPortIpAddress();
final List<NiciraNvpTag> tags = new ArrayList<NiciraNvpTag>();
tags.add(new NiciraNvpTag("cs_account", command.getOwnerName()));
final long networkId = command.getNetworkId();
final NiciraNvpApi niciraNvpApi = niciraNvpResource.getNiciraNvpApi();


log.info("attaching logical switch on logical router for shared network");
}

};