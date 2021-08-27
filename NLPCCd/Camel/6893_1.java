//,temp,sample_6347.java,2,8,temp,sample_2276.java,2,8
//,3
public class xxx {
public void execute() {
DomainMetadataRequest request = new DomainMetadataRequest() .withDomainName(determineDomainName());
DomainMetadataResult result = this.sdbClient.domainMetadata(request);


log.info("received result");
}

};