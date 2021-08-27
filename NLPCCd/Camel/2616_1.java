//,temp,sample_5210.java,2,17,temp,sample_7611.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS))) {
instanceIds = exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS, Collection.class);
request.withInstanceIds(instanceIds);
} else {
throw new IllegalArgumentException("Instances Ids must be specified");
}
UnmonitorInstancesResult result;
try {
result = ec2Client.unmonitorInstances(request);
} catch (AmazonServiceException ase) {


log.info("unmonitor instances command returned the error code");
}
}

};