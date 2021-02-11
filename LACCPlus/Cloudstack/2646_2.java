//,temp,JuniperSrxResource.java,757,783,temp,JuniperSrxResource.java,733,755
//,3
public class xxx {
    private void implementGuestNetwork(GuestNetworkType type, Long publicVlanTag, String publicIp, long privateVlanTag, String privateGateway, String privateSubnet,
        long privateCidrNumber) throws ExecutionException {
        privateGateway = privateGateway + "/" + privateCidrNumber;
        privateSubnet = privateSubnet + "/" + privateCidrNumber;

        managePrivateInterface(SrxCommand.ADD, !type.equals(GuestNetworkType.SOURCE_NAT), privateVlanTag, privateGateway);
        manageZoneInterface(SrxCommand.ADD, privateVlanTag);

        if (type.equals(GuestNetworkType.SOURCE_NAT)) {
            manageSourceNatPool(SrxCommand.ADD, publicIp);
            manageSourceNatRule(SrxCommand.ADD, publicIp, privateSubnet);
            manageProxyArp(SrxCommand.ADD, publicVlanTag, publicIp);
            manageUsageFilter(SrxCommand.ADD, _usageFilterIPOutput, privateSubnet, null, genIpFilterTermName(publicIp));
            manageUsageFilter(SrxCommand.ADD, _usageFilterIPInput, publicIp, null, genIpFilterTermName(publicIp));
        } else if (type.equals(GuestNetworkType.INTERFACE_NAT)) {
            manageUsageFilter(SrxCommand.ADD, _usageFilterVlanOutput, null, privateVlanTag, null);
            manageUsageFilter(SrxCommand.ADD, _usageFilterVlanInput, null, privateVlanTag, null);
        }

        String msg = "Implemented guest network with type " + type + ". Guest VLAN tag: " + privateVlanTag + ", guest gateway: " + privateGateway;
        msg += type.equals(GuestNetworkType.SOURCE_NAT) ? ", source NAT IP: " + publicIp : "";
        s_logger.debug(msg);
    }

};