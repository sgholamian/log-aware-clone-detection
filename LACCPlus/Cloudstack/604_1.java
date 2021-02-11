//,temp,JuniperSrxResource.java,2412,2467,temp,JuniperSrxResource.java,1664,1716
//,3
public class xxx {
    private boolean manageSourceNatPool(SrxCommand command, String publicIp) throws ExecutionException {
        String poolName = genSourceNatPoolName(publicIp);
        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.SRC_NAT_POOL_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "pool-name", poolName);
                return sendRequestAndCheckResponse(command, xml, "name", poolName);

            case CHECK_IF_IN_USE:
                // Check if any source nat rules refer to this pool
                xml = SrxXml.SRC_NAT_RULE_GETALL.getXml();
                return sendRequestAndCheckResponse(command, xml, "pool-name", poolName);

            case ADD:
                if (manageSourceNatPool(SrxCommand.CHECK_IF_EXISTS, publicIp)) {
                    return true;
                }

                xml = SrxXml.SRC_NAT_POOL_ADD.getXml();
                xml = replaceXmlValue(xml, "pool-name", poolName);
                xml = replaceXmlValue(xml, "address", publicIp + "/32");

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add source NAT pool for public IP " + publicIp);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageSourceNatPool(SrxCommand.CHECK_IF_EXISTS, publicIp)) {
                    return true;
                }

                if (manageSourceNatPool(SrxCommand.CHECK_IF_IN_USE, publicIp)) {
                    return true;
                }

                xml = SrxXml.SRC_NAT_POOL_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "pool-name", poolName);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to delete source NAT pool for public IP " + publicIp);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};