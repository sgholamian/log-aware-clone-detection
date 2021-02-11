//,temp,JuniperSrxResource.java,1664,1716,temp,JuniperSrxResource.java,1430,1483
//,3
public class xxx {
    private boolean manageIkeGateway(SrxCommand command, String ikeGatewayName, Long accountId, String ikePolicyName, String ikeGatewayHostname, String username)
        throws ExecutionException {
        if (ikeGatewayName == null) {
            ikeGatewayName = genIkeGatewayName(accountId, username);
        }

        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.IKE_GATEWAY_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "gateway-name", ikeGatewayName);
                return sendRequestAndCheckResponse(command, xml, "name", ikeGatewayName);

            case ADD:
                if (manageIkeGateway(SrxCommand.CHECK_IF_EXISTS, ikeGatewayName, accountId, ikePolicyName, ikeGatewayHostname, username)) {
                    return true;
                }

                xml = SrxXml.IKE_GATEWAY_ADD.getXml();
                xml = replaceXmlValue(xml, "gateway-name", ikeGatewayName);
                xml = replaceXmlValue(xml, "ike-policy-name", ikePolicyName);
                xml = replaceXmlValue(xml, "ike-gateway-hostname", ikeGatewayHostname);
                xml = replaceXmlValue(xml, "public-interface-name", _publicInterface);
                xml = replaceXmlValue(xml, "access-profile-name", genAccessProfileName(accountId, username));

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add IKE gateway: " + ikeGatewayName);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageIkeGateway(SrxCommand.CHECK_IF_EXISTS, ikeGatewayName, accountId, ikePolicyName, ikeGatewayHostname, username)) {
                    return true;
                }

                xml = SrxXml.IKE_GATEWAY_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "gateway-name", ikeGatewayName);

                if (!sendRequestAndCheckResponse(command, xml, "name", ikeGatewayName)) {
                    throw new ExecutionException("Failed to delete IKE gateway: " + ikeGatewayName);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};