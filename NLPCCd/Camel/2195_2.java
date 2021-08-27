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
MonitorInstancesResult result;
try {
result = ec2Client.monitorInstances(request);
} catch (AmazonServiceException ase) {


log.info("monitor instances command returned the error code");
}
}

};