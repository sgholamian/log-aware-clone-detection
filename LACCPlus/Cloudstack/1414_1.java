//,temp,JuniperSrxResource.java,1664,1716,temp,JuniperSrxResource.java,1604,1658
//,3
public class xxx {
    private boolean manageAccessProfile(SrxCommand command, String accessProfileName, Long accountId, String username, String password, String addressPoolName)
        throws ExecutionException {
        if (accessProfileName == null) {
            accessProfileName = genAccessProfileName(accountId, username);
        }

        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.ACCESS_PROFILE_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "access-profile-name", accessProfileName);
                return sendRequestAndCheckResponse(command, xml, "name", username);

            case ADD:
                if (manageAccessProfile(SrxCommand.CHECK_IF_EXISTS, accessProfileName, accountId, username, password, addressPoolName)) {
                    return true;
                }

                xml = SrxXml.ACCESS_PROFILE_ADD.getXml();
                xml = replaceXmlValue(xml, "access-profile-name", accessProfileName);
                xml = replaceXmlValue(xml, "username", username);
                xml = replaceXmlValue(xml, "password", password);
                xml = replaceXmlValue(xml, "address-pool-name", addressPoolName);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add access profile: " + accessProfileName);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageAccessProfile(SrxCommand.CHECK_IF_EXISTS, accessProfileName, accountId, username, password, addressPoolName)) {
                    return true;
                }

                xml = SrxXml.ACCESS_PROFILE_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "access-profile-name", accessProfileName);

                if (!sendRequestAndCheckResponse(command, xml, "name", username)) {
                    throw new ExecutionException("Failed to delete access profile: " + accessProfileName);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};