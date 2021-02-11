//,temp,JuniperSrxResource.java,2551,2621,temp,JuniperSrxResource.java,1722,1776
//,3
public class xxx {
    private boolean manageAddressBookEntry(SrxCommand command, String zone, String ip, String entryName) throws ExecutionException {
        if (!zone.equals(_publicZone) && !zone.equals(_privateZone)) {
            throw new ExecutionException("Invalid zone.");
        }

        if (entryName == null) {
            entryName = genAddressBookEntryName(ip);
        }

        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.ADDRESS_BOOK_ENTRY_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "zone", zone);
                xml = replaceXmlValue(xml, "entry-name", entryName);
                return sendRequestAndCheckResponse(command, xml, "name", entryName);

            case CHECK_IF_IN_USE:
                // Check if any security policies refer to this address book entry
                xml = SrxXml.SECURITY_POLICY_GETALL.getXml();
                String fromZone = zone.equals(_publicZone) ? _privateZone : _publicZone;
                xml = replaceXmlValue(xml, "from-zone", fromZone);
                xml = replaceXmlValue(xml, "to-zone", zone);
                return sendRequestAndCheckResponse(command, xml, "destination-address", entryName);

            case ADD:
                if (manageAddressBookEntry(SrxCommand.CHECK_IF_EXISTS, zone, ip, entryName)) {
                    return true;
                }

                xml = SrxXml.ADDRESS_BOOK_ENTRY_ADD.getXml();
                xml = replaceXmlValue(xml, "zone", zone);
                xml = replaceXmlValue(xml, "entry-name", entryName);
                xml = replaceXmlValue(xml, "ip", ip);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add address book entry for IP " + ip + " in zone " + zone);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageAddressBookEntry(SrxCommand.CHECK_IF_EXISTS, zone, ip, entryName)) {
                    return true;
                }

                if (manageAddressBookEntry(SrxCommand.CHECK_IF_IN_USE, zone, ip, entryName)) {
                    return true;
                }

                xml = SrxXml.ADDRESS_BOOK_ENTRY_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "zone", zone);
                xml = replaceXmlValue(xml, "entry-name", entryName);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to delete address book entry for IP " + ip + " in zone " + zone);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;

        }

    }

};