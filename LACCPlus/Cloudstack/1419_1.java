//,temp,JuniperSrxResource.java,3133,3203,temp,JuniperSrxResource.java,2551,2621
//,3
public class xxx {
    private boolean manageUsageFilter(SrxCommand command, UsageFilter filter, String ip, Long guestVlanTag, String filterTermName) throws ExecutionException {
        String filterName;
        String filterDescription;
        String xml;

        if (filter.equals(_usageFilterIPInput) || filter.equals(_usageFilterIPOutput)) {
            assert (ip != null && guestVlanTag == null);
            filterName = filter.getName();
            filterDescription = filter.toString() + ", public IP = " + ip;
            xml = SrxXml.PUBLIC_IP_FILTER_TERM_ADD.getXml();
        } else if (filter.equals(_usageFilterVlanInput) || filter.equals(_usageFilterVlanOutput)) {
            assert (ip == null && guestVlanTag != null);
            filterName = filter.getName() + "-" + guestVlanTag;
            filterDescription = filter.toString() + ", guest VLAN tag = " + guestVlanTag;
            filterTermName = filterName;
            xml = SrxXml.GUEST_VLAN_FILTER_TERM_ADD.getXml();
        } else {
            return false;
        }

        switch (command) {

            case CHECK_IF_EXISTS:
                xml = SrxXml.FILTER_TERM_GETONE.getXml();
                xml = setDelete(xml, false);
                xml = replaceXmlValue(xml, "filter-name", filterName);
                xml = replaceXmlValue(xml, "term-name", filterTermName);
                return sendRequestAndCheckResponse(command, xml, "name", filterTermName);

            case ADD:
                if (manageUsageFilter(SrxCommand.CHECK_IF_EXISTS, filter, ip, guestVlanTag, filterTermName)) {
                    return true;
                }

                xml = replaceXmlValue(xml, "filter-name", filterName);
                xml = replaceXmlValue(xml, "term-name", filterTermName);

                if (filter.equals(_usageFilterIPInput) || filter.equals(_usageFilterIPOutput)) {
                    xml = replaceXmlValue(xml, "ip-address", ip);
                    xml = replaceXmlValue(xml, "address-type", filter.getAddressType());
                }

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to add usage filter: " + filterDescription);
                } else {
                    return true;
                }

            case DELETE:
                if (!manageUsageFilter(SrxCommand.CHECK_IF_EXISTS, filter, ip, guestVlanTag, filterTermName)) {
                    return true;
                }

                boolean deleteFilter = filter.equals(_usageFilterVlanInput) || filter.equals(_usageFilterVlanOutput);
                xml = deleteFilter ? SrxXml.FILTER_GETONE.getXml() : SrxXml.FILTER_TERM_GETONE.getXml();
                xml = setDelete(xml, true);
                xml = replaceXmlValue(xml, "filter-name", filterName);
                xml = !deleteFilter ? replaceXmlValue(xml, "term-name", filterTermName) : xml;

                if (!sendRequestAndCheckResponse(command, xml)) {
                    throw new ExecutionException("Failed to delete usage filter: " + filterDescription);
                } else {
                    return true;
                }

            default:
                s_logger.debug("Unrecognized command.");
                return false;

        }
    }

};