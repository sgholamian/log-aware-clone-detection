//,temp,LibvirtComputingResource.java,3223,3243,temp,LibvirtComputingResource.java,3115,3132
//,3
public class xxx {
    public List<InterfaceDef> getInterfaces(final Connect conn, final String vmName) {
        final LibvirtDomainXMLParser parser = new LibvirtDomainXMLParser();
        Domain dm = null;
        try {
            dm = conn.domainLookupByName(vmName);
            parser.parseDomainXML(dm.getXMLDesc(0));
            return parser.getInterfaces();

        } catch (final LibvirtException e) {
            s_logger.debug("Failed to get dom xml: " + e.toString());
            return new ArrayList<InterfaceDef>();
        } finally {
            try {
                if (dm != null) {
                    dm.free();
                }
            } catch (final LibvirtException e) {
                s_logger.trace("Ignoring libvirt error.", e);
            }
        }
    }

};