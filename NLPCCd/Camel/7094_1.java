//,temp,sample_6437.java,2,8,temp,sample_3238.java,2,8
//,3
public class xxx {
public void execute() {
GetAttributesRequest request = new GetAttributesRequest() .withDomainName(determineDomainName()) .withItemName(determineItemName()) .withConsistentRead(determineConsistentRead()) .withAttributeNames(determineAttributeNames());
GetAttributesResult result = this.sdbClient.getAttributes(request);


log.info("received result");
}

};