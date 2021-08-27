//,temp,sample_156.java,2,11,temp,sample_157.java,2,11
//,3
public class xxx {
private void deleteFunction(AWSLambda lambdaClient, Exchange exchange) {
DeleteFunctionResult result;
try {
result = lambdaClient.deleteFunction(new DeleteFunctionRequest().withFunctionName(getConfiguration().getFunction()));
} catch (AmazonServiceException ase) {


log.info("deletefunction command returned the error code");
}
}

};