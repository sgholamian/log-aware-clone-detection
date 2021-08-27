//,temp,sample_1290.java,2,7,temp,sample_1291.java,2,8
//,3
public class xxx {
public void execute() {
DeleteAttributesRequest request = new DeleteAttributesRequest() .withDomainName(determineDomainName()) .withItemName(determineItemName()) .withExpected(determineUpdateCondition()) .withAttributes(determineAttributes());
this.sdbClient.deleteAttributes(request);


log.info("request sent");
}

};