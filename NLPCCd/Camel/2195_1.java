//,temp,sample_5200.java,2,17,temp,sample_5208.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS))) {
instanceIds = exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS, Collection.class);
request.withInstanceIds(instanceIds);
} else {
throw new IllegalArgumentException("Instances Ids must be specified");
}
StopInstancesResult result;
try {
result = ec2Client.stopInstances(request);
} catch (AmazonServiceException ase) {


log.info("stop instances command returned the error code");
}
}

};