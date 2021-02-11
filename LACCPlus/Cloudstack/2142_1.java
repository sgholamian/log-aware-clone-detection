//,temp,JuniperSrxResource.java,2477,2537,temp,JuniperSrxResource.java,1430,1483
//,3
public class xxx {
    private boolean manageSourceNatRule(SrxCommand command, String publicIp, String privateSubnet) throws ExecutionException {
        String ruleName = genSourceNatRuleName(publicIp, privateSubnet);
        String poolName = genSourceNatPoolName(publicIp);
        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.SRC_NAT_RULE_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "rule-set", _privateZone);
                xml = replaceXmlValue(xml, "from-zone", _privateZone);
                xml = replaceXmlValue(xml, "rule-name", ruleName);
                return sendRequestAndCheckResponse(command, xml, "name", ruleName);

            case ADD:
                if (manageSourceNatRule(SrxCommand.CHECK_IF_EXISTS, publicIp, privateSubnet)) {
                    return true;
                }

                if (!manageSourceNatPool(SrxCommand.CHECK_IF_EXISTS, publicIp)) {
                    throw new ExecutionException("The source NAT pool corresponding to " + publicIp + " does not exist.");
                }

                xml = SrxXml.SRC_NAT_RULE_ADD.getXml();
                xml = replaceXmlValue(xml, "rule-set", _privateZone);
                xml = replaceXmlValue(xml, "from-zone", _privateZone);
                xml = replaceXmlValue(xml, "to-zone", _publicZone);
                xml = replaceXmlValue(xml, "rule-name", ruleName);
                xml = replaceXmlValue(xml, "private-subnet", privateSubnet);
                xml = replaceXmlValue(xml, "pool-name", poolName);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add source NAT rule for public IP " + publicIp + " and private subnet " + privateSubnet);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageSourceNatRule(SrxCommand.CHECK_IF_EXISTS, publicIp, privateSubnet)) {
                    return true;
                }

                xml = SrxXml.SRC_NAT_RULE_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "rule-set", _privateZone);
                xml = replaceXmlValue(xml, "from-zone", _privateZone);
                xml = replaceXmlValue(xml, "rule-name", ruleName);

                if (!sendRequestAndCheckResponse(command, xml, "name", ruleName)) {
                    throw new ExecutionException("Failed to delete source NAT rule for public IP " + publicIp + " and private subnet " + privateSubnet);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }

    }

};