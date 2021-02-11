//,temp,LibvirtComputingResource.java,3245,3265,temp,LibvirtComputingResource.java,3115,3132
//,3
public class xxx {
    public Integer getVncPort(final Connect conn, final String vmName) throws LibvirtException {
        final LibvirtDomainXMLParser parser = new LibvirtDomainXMLParser();
        Domain dm = null;
        try {
            dm = conn.domainLookupByName(vmName);
            final String xmlDesc = dm.getXMLDesc(0);
            parser.parseDomainXML(xmlDesc);
            return parser.getVncPort();
        } finally {
            try {
                if (dm != null) {
                    dm.free();
                }
            } catch (final LibvirtException l) {
                s_logger.trace("Ignoring libvirt error.", l);
            }
        }
    }

};