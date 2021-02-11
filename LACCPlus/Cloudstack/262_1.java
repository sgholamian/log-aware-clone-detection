//,temp,JuniperSrxResource.java,1264,1275,temp,JuniperSrxResource.java,979,989
//,3
public class xxx {
    private void removeDestinationNatRule(Long publicVlanTag, String publicIp, String privateIp, int srcPort, int destPort) throws ExecutionException {
        manageDestinationNatRule(SrxCommand.DELETE, publicIp, privateIp, srcPort, destPort);
        manageDestinationNatPool(SrxCommand.DELETE, privateIp, destPort);
        manageProxyArp(SrxCommand.DELETE, publicVlanTag, publicIp);

        removeSecurityPolicyAndApplications(SecurityPolicyType.DESTINATION_NAT, privateIp);

        manageAddressBookEntry(SrxCommand.DELETE, _privateZone, privateIp, null);

        s_logger.debug("Removed destination NAT rule for public IP " + publicIp + ", private IP " + privateIp + ", source port " + srcPort + ", and dest port " +
            destPort);
    }

};