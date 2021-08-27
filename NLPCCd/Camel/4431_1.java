//,temp,sample_359.java,2,8,temp,sample_3160.java,2,8
//,2
public class xxx {
public void execute() {
BatchDeleteAttributesRequest request = new BatchDeleteAttributesRequest() .withDomainName(determineDomainName()) .withItems(determineDeletableItems());
this.sdbClient.batchDeleteAttributes(request);


log.info("request sent");
}

};