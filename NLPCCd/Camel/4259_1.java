//,temp,sample_5615.java,2,15,temp,sample_5614.java,2,14
//,3
public class xxx {
public void doStart() throws Exception {
super.doStart();
sdbClient = configuration.getAmazonSDBClient() != null ? configuration.getAmazonSDBClient() : createSdbClient();
String domainName = getConfiguration().getDomainName();
try {
sdbClient.domainMetadata(new DomainMetadataRequest(domainName));
return;
} catch (NoSuchDomainException ase) {
sdbClient.createDomain(new CreateDomainRequest(domainName));


log.info("domain created");
}
}

};