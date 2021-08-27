//,temp,sample_5197.java,2,16,temp,sample_5212.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EC2Constants.INSTANCES_TAGS))) {
tags = exchange.getIn().getHeader(EC2Constants.INSTANCES_TAGS, Collection.class);
request.withTags(tags);
} else {
throw new IllegalArgumentException("Tags must be specified");
}
CreateTagsResult result = new CreateTagsResult();
try {
result = ec2Client.createTags(request);
} catch (AmazonServiceException ase) {


log.info("create tags command returned the error code");
}
}

};