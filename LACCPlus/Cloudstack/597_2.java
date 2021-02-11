//,temp,JuniperSrxResource.java,1546,1598,temp,JuniperSrxResource.java,1489,1540
//,3
public class xxx {
    private boolean manageIpsecVpn(SrxCommand command, String ipsecVpnName, Long accountId, String guestNetworkCidr, String username, String ipsecPolicyName)
        throws ExecutionException {
        if (ipsecVpnName == null) {
            ipsecVpnName = genIpsecVpnName(accountId, username);
        }

        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.IPSEC_VPN_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "ipsec-vpn-name", ipsecVpnName);
                return sendRequestAndCheckResponse(command, xml, "name", ipsecVpnName);

            case ADD:
                if (manageIpsecVpn(SrxCommand.CHECK_IF_EXISTS, ipsecVpnName, accountId, guestNetworkCidr, username, ipsecPolicyName)) {
                    return true;
                }

                xml = SrxXml.IPSEC_VPN_ADD.getXml();
                xml = replaceXmlValue(xml, "ipsec-vpn-name", ipsecVpnName);
                xml = replaceXmlValue(xml, "ike-gateway", genIkeGatewayName(accountId, username));
                xml = replaceXmlValue(xml, "ipsec-policy-name", ipsecPolicyName);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add IPSec VPN: " + ipsecVpnName);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageIpsecVpn(SrxCommand.CHECK_IF_EXISTS, ipsecVpnName, accountId, guestNetworkCidr, username, ipsecPolicyName)) {
                    return true;
                }

                xml = SrxXml.IPSEC_VPN_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "ipsec-vpn-name", ipsecVpnName);

                if (!sendRequestAndCheckResponse(command, xml, "name", ipsecVpnName)) {
                    throw new ExecutionException("Failed to delete IPSec VPN: " + ipsecVpnName);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};