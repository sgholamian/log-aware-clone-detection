//,temp,sample_5214.java,2,17,temp,sample_5207.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EC2Constants.INSTANCES_TAGS))) {
tags = exchange.getIn().getHeader(EC2Constants.INSTANCES_TAGS, Collection.class);
request.withTags(tags);
} else {
throw new IllegalArgumentException("Tags must be specified");
}
DeleteTagsResult result = new DeleteTagsResult();
try {
result = ec2Client.deleteTags(request);
} catch (AmazonServiceException ase) {


log.info("delete tags command returned the error code");
}
}

};