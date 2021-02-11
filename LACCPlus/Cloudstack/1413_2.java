//,temp,JuniperSrxResource.java,1546,1598,temp,JuniperSrxResource.java,1373,1424
//,3
public class xxx {
    private boolean manageIkePolicy(SrxCommand command, String ikePolicyName, Long accountId, String preSharedKey) throws ExecutionException {
        if (ikePolicyName == null) {
            ikePolicyName = genIkePolicyName(accountId);
        }

        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.IKE_GATEWAY_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "policy-name", ikePolicyName);
                return sendRequestAndCheckResponse(command, xml, "name", ikePolicyName);

            case ADD:
                if (manageIkePolicy(SrxCommand.CHECK_IF_EXISTS, ikePolicyName, accountId, preSharedKey)) {
                    return true;
                }

                xml = SrxXml.IKE_POLICY_ADD.getXml();
                xml = replaceXmlValue(xml, "policy-name", ikePolicyName);
                xml = replaceXmlValue(xml, "proposal-name", _ikeProposalName);
                xml = replaceXmlValue(xml, "pre-shared-key", preSharedKey);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add IKE policy: " + ikePolicyName);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageIkePolicy(SrxCommand.CHECK_IF_EXISTS, ikePolicyName, accountId, preSharedKey)) {
                    return true;
                }

                xml = SrxXml.IKE_GATEWAY_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "policy-name", ikePolicyName);

                if (!sendRequestAndCheckResponse(command, xml, "name", ikePolicyName)) {
                    throw new ExecutionException("Failed to delete IKE policy: " + ikePolicyName);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }

    }

};