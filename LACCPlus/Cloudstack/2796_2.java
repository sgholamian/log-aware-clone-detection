//,temp,JuniperSrxResource.java,2412,2467,temp,JuniperSrxResource.java,1954,2002
//,3
public class xxx {
    private boolean manageZoneInterface(SrxCommand command, long vlanTag) throws ExecutionException {
        String zoneInterfaceName = genZoneInterfaceName(vlanTag);
        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.ZONE_INTERFACE_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "private-zone-name", _privateZone);
                xml = replaceXmlValue(xml, "zone-interface-name", zoneInterfaceName);
                return sendRequestAndCheckResponse(command, xml, "name", zoneInterfaceName);

            case ADD:
                if (manageZoneInterface(SrxCommand.CHECK_IF_EXISTS, vlanTag)) {
                    return true;
                }

                xml = SrxXml.ZONE_INTERFACE_ADD.getXml();
                xml = replaceXmlValue(xml, "private-zone-name", _privateZone);
                xml = replaceXmlValue(xml, "zone-interface-name", zoneInterfaceName);

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add interface " + zoneInterfaceName + " to zone " + _privateZone);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageZoneInterface(SrxCommand.CHECK_IF_EXISTS, vlanTag)) {
                    return true;
                }

                xml = SrxXml.ZONE_INTERFACE_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "private-zone-name", _privateZone);
                xml = replaceXmlValue(xml, "zone-interface-name", zoneInterfaceName);

                if (!sendRequestAndCheckResponse(command, xml, "name", zoneInterfaceName)) {
                    throw new ExecutionException("Failed to delete interface " + zoneInterfaceName + " from zone " + _privateZone);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }
    }

};