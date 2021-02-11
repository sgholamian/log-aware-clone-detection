//,temp,JuniperSrxResource.java,1546,1598,temp,JuniperSrxResource.java,1430,1483
//,3
public class xxx {
    private boolean manageDynamicVpnClient(SrxCommand command, String clientName, Long accountId, String guestNetworkCidr, String ipsecVpnName, String username)
        throws ExecutionException {
        if (clientName == null) {
            clientName = genDynamicVpnClientName(accountId, username);
        }

        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.DYNAMIC_VPN_CLIENT_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "client-name", clientName);
                return sendRequestAndCheckResponse(command, xml, "name", clientName);

            case ADD:
                if (manageDynamicVpnClient(SrxCommand.CHECK_IF_EXISTS, clientName, accountId, guestNetworkCidr, ipsecVpnName, username)) {
                    return true;
                }

                xml = SrxXml.DYNAMIC_VPN_CLIENT_ADD.getXml();
                xml = replaceXmlValue(xml, "client-name", clientName);
                xml = replaceXmlValue(xml, "guest-network-cidr", guestNetworkCidr);
                xml = replaceXmlValue(xml, "ipsec-vpn-name", ipsecVpnName);
                xml = replaceXmlValue(xml, "username", username);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add dynamic VPN client: " + clientName);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageDynamicVpnClient(SrxCommand.CHECK_IF_EXISTS, clientName, accountId, guestNetworkCidr, ipsecVpnName, username)) {
                    return true;
                }

                xml = SrxXml.DYNAMIC_VPN_CLIENT_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "client-name", clientName);

                if (!sendRequestAndCheckResponse(command, xml, "name", clientName)) {
                    throw new ExecutionException("Failed to delete dynamic VPN client: " + clientName);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};