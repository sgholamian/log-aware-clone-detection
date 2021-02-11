//,temp,PaloAltoResource.java,524,542,temp,PaloAltoResource.java,506,522
//,3
public class xxx {
    private void shutdownGuestNetwork(ArrayList<IPaloAltoCommand> cmdList, GuestNetworkType type, Long publicVlanTag, String sourceNatIpAddress, long privateVlanTag,
        String privateGateway, String privateSubnet, long privateCidrSize) throws ExecutionException {
        privateSubnet = privateSubnet + "/" + privateCidrSize;

        // remove any orphaned egress rules if they exist...
        removeOrphanedFirewallRules(cmdList, privateVlanTag);

        if (type.equals(GuestNetworkType.SOURCE_NAT)) {
            manageNetworkIsolation(cmdList, PaloAltoPrimative.DELETE, privateVlanTag, privateSubnet, privateGateway);
            manageSrcNatRule(cmdList, PaloAltoPrimative.DELETE, type, publicVlanTag, sourceNatIpAddress + "/32", privateVlanTag, privateGateway + "/" + privateCidrSize);
            managePublicInterface(cmdList, PaloAltoPrimative.DELETE, publicVlanTag, sourceNatIpAddress + "/32", privateVlanTag);
        }

        managePrivateInterface(cmdList, PaloAltoPrimative.DELETE, privateVlanTag, privateGateway + "/" + privateCidrSize);

        String msg = "Shut down guest network with type " + type + ". Guest VLAN tag: " + privateVlanTag + ", guest gateway: " + privateGateway + "/" + privateCidrSize;
        msg += type.equals(GuestNetworkType.SOURCE_NAT) ? ", source NAT IP: " + sourceNatIpAddress : "";
        s_logger.debug(msg);
    }

};