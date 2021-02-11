//,temp,sample_4156.java,2,14,temp,sample_4158.java,2,14
//,2
public class xxx {
public List<DiskDef> getDisks(final Connect conn, final String vmName) {
final LibvirtDomainXMLParser parser = new LibvirtDomainXMLParser();
Domain dm = null;
try {
dm = conn.domainLookupByName(vmName);
parser.parseDomainXML(dm.getXMLDesc(0));
return parser.getDisks();
} catch (final LibvirtException e) {


log.info("failed to get dom xml");
}
}

};