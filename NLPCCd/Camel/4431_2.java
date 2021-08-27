//,temp,sample_359.java,2,8,temp,sample_3160.java,2,8
//,2
public class xxx {
public void execute() {
BatchPutAttributesRequest request = new BatchPutAttributesRequest() .withDomainName(determineDomainName()) .withItems(determineReplaceableItems());
this.sdbClient.batchPutAttributes(request);


log.info("request sent");
}

};