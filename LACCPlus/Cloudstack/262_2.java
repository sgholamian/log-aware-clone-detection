//,temp,JuniperSrxResource.java,1264,1275,temp,JuniperSrxResource.java,979,989
//,3
public class xxx {
    private void removeStaticNatRule(Long publicVlanTag, String publicIp, String privateIp) throws ExecutionException {
        manageStaticNatRule(SrxCommand.DELETE, publicIp, privateIp);

        // Remove any existing security policy and clean up applications
        removeSecurityPolicyAndApplications(SecurityPolicyType.STATIC_NAT, privateIp);

        manageAddressBookEntry(SrxCommand.DELETE, _privateZone, privateIp, null);
        manageProxyArp(SrxCommand.DELETE, publicVlanTag, publicIp);

        s_logger.debug("Removed static NAT rule for public IP " + publicIp + ", and private IP " + privateIp);
    }

};