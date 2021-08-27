//,temp,sample_5205.java,2,17,temp,sample_5198.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS))) {
instanceIds = exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS, Collection.class);
request.withInstanceIds(instanceIds);
} else {
throw new IllegalArgumentException("Instances Ids must be specified");
}
StartInstancesResult result;
try {
result = ec2Client.startInstances(request);
} catch (AmazonServiceException ase) {


log.info("start instances command returned the error code");
}
}

};