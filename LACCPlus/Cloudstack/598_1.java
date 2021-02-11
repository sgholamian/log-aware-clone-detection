//,temp,JuniperSrxResource.java,1604,1658,temp,JuniperSrxResource.java,1546,1598
//,3
public class xxx {
    private boolean manageAddressPool(SrxCommand command, String addressPoolName, Long accountId, String guestNetworkCidr, String lowAddress, String highAddress,
        String primaryDnsAddress) throws ExecutionException {
        if (addressPoolName == null) {
            addressPoolName = genAddressPoolName(accountId);
        }

        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.ADDRESS_POOL_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "address-pool-name", addressPoolName);
                return sendRequestAndCheckResponse(command, xml, "name", addressPoolName);

            case ADD:
                if (manageAddressPool(SrxCommand.CHECK_IF_EXISTS, addressPoolName, accountId, guestNetworkCidr, lowAddress, highAddress, primaryDnsAddress)) {
                    return true;
                }

                xml = SrxXml.ADDRESS_POOL_ADD.getXml();
                xml = replaceXmlValue(xml, "address-pool-name", addressPoolName);
                xml = replaceXmlValue(xml, "guest-network-cidr", guestNetworkCidr);
                xml = replaceXmlValue(xml, "address-range-name", "r-" + addressPoolName);
                xml = replaceXmlValue(xml, "low-address", lowAddress);
                xml = replaceXmlValue(xml, "high-address", highAddress);
                xml = replaceXmlValue(xml, "primary-dns-address", primaryDnsAddress);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add address pool: " + addressPoolName);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageAddressPool(SrxCommand.CHECK_IF_EXISTS, addressPoolName, accountId, guestNetworkCidr, lowAddress, highAddress, primaryDnsAddress)) {
                    return true;
                }

                xml = SrxXml.ADDRESS_POOL_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "address-pool-name", addressPoolName);

                if (!sendRequestAndCheckResponse(command, xml, "name", addressPoolName)) {
                    throw new ExecutionException("Failed to delete address pool: " + addressPoolName);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};