//,temp,JuniperSrxResource.java,757,783,temp,JuniperSrxResource.java,733,755
//,3
public class xxx {
    private void shutdownGuestNetwork(GuestNetworkType type, long accountId, Long publicVlanTag, String sourceNatIpAddress, long privateVlanTag, String privateGateway,
        String privateSubnet, long privateCidrSize) throws ExecutionException {
        // Remove static and destination NAT rules for the guest network
        removeStaticAndDestNatRulesInPrivateVlan(privateVlanTag, privateGateway, privateCidrSize);

        privateGateway = privateGateway + "/" + privateCidrSize;
        privateSubnet = privateSubnet + "/" + privateCidrSize;

        managePrivateInterface(SrxCommand.DELETE, false, privateVlanTag, privateGateway);
        manageZoneInterface(SrxCommand.DELETE, privateVlanTag);
        deleteVpnObjectsForAccount(accountId);

        if (type.equals(GuestNetworkType.SOURCE_NAT)) {
            manageSourceNatRule(SrxCommand.DELETE, sourceNatIpAddress, privateSubnet);
            manageSourceNatPool(SrxCommand.DELETE, sourceNatIpAddress);
            manageProxyArp(SrxCommand.DELETE, publicVlanTag, sourceNatIpAddress);
            manageUsageFilter(SrxCommand.DELETE, _usageFilterIPOutput, privateSubnet, null, genIpFilterTermName(sourceNatIpAddress));
            manageUsageFilter(SrxCommand.DELETE, _usageFilterIPInput, sourceNatIpAddress, null, genIpFilterTermName(sourceNatIpAddress));
        } else if (type.equals(GuestNetworkType.INTERFACE_NAT)) {
            manageUsageFilter(SrxCommand.DELETE, _usageFilterVlanOutput, null, privateVlanTag, null);
            manageUsageFilter(SrxCommand.DELETE, _usageFilterVlanInput, null, privateVlanTag, null);
        }

        String msg = "Shut down guest network with type " + type + ". Guest VLAN tag: " + privateVlanTag + ", guest gateway: " + privateGateway;
        msg += type.equals(GuestNetworkType.SOURCE_NAT) ? ", source NAT IP: " + sourceNatIpAddress : "";
        s_logger.debug(msg);
    }

};