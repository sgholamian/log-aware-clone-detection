//,temp,sample_5214.java,2,17,temp,sample_5207.java,2,17
//,3
public class xxx {
public void dummy_method(){
RebootInstancesRequest request = new RebootInstancesRequest();
if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS))) {
instanceIds = exchange.getIn().getHeader(EC2Constants.INSTANCES_IDS, Collection.class);
request.withInstanceIds(instanceIds);
} else {
throw new IllegalArgumentException("Instances Ids must be specified");
}
try {
ec2Client.rebootInstances(request);
} catch (AmazonServiceException ase) {


log.info("reboot instances command returned the error code");
}
}

};