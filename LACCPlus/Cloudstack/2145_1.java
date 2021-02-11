//,temp,JuniperSrxResource.java,1722,1776,temp,JuniperSrxResource.java,1604,1658
//,3
public class xxx {
    private boolean managePrivateInterface(SrxCommand command, boolean addFilters, long vlanTag, String privateInterfaceIp) throws ExecutionException {
        String xml;

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.PRIVATE_INTERFACE_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "private-interface-name", _privateInterface);
                xml = replaceXmlValue(xml, "vlan-id", String.valueOf(vlanTag));
                return sendRequestAndCheckResponse(command, xml, "name", String.valueOf(vlanTag));

            case ADD:
                if (managePrivateInterface(SrxCommand.CHECK_IF_EXISTS, false, vlanTag, privateInterfaceIp)) {
                    return true;
                }

                xml = addFilters ? SrxXml.PRIVATE_INTERFACE_WITH_FILTERS_ADD.getXml() : SrxXml.PRIVATE_INTERFACE_ADD.getXml();
                xml = replaceXmlValue(xml, "private-interface-name", _privateInterface);
                xml = replaceXmlValue(xml, "vlan-id", String.valueOf(vlanTag));
                xml = replaceXmlValue(xml, "private-interface-ip", privateInterfaceIp);

                if (addFilters) {
                    xml = replaceXmlValue(xml, "input-filter-name", _usageFilterVlanInput.getName() + "-" + vlanTag);
                    xml = replaceXmlValue(xml, "output-filter-name", _usageFilterVlanOutput.getName() + "-" + vlanTag);
                }

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add private interface for guest VLAN tag " + vlanTag);
                } else {
                    return true;
                }

            case DELETE:
                if (!managePrivateInterface(SrxCommand.CHECK_IF_EXISTS, false, vlanTag, privateInterfaceIp)) {
                    return true;
                }

                xml = SrxXml.PRIVATE_INTERFACE_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "private-interface-name", _privateInterface);
                xml = replaceXmlValue(xml, "vlan-id", String.valueOf(vlanTag));

                if (!sendRequestAndCheckResponse(command, xml, "name", String.valueOf(vlanTag))) {
                    throw new ExecutionException("Failed to delete private interface for guest VLAN tag " + vlanTag);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;
        }

    }

};