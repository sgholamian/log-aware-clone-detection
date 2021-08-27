//,temp,sample_6347.java,2,8,temp,sample_2276.java,2,8
//,3
public class xxx {
public void execute() {
DeleteDomainRequest request = new DeleteDomainRequest() .withDomainName(determineDomainName());
this.sdbClient.deleteDomain(request);


log.info("request sent");
}

};