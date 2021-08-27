//,temp,sample_156.java,2,11,temp,sample_157.java,2,11
//,3
public class xxx {
private void getFunction(AWSLambda lambdaClient, Exchange exchange) {
GetFunctionResult result;
try {
result = lambdaClient.getFunction(new GetFunctionRequest().withFunctionName(getConfiguration().getFunction()));
} catch (AmazonServiceException ase) {


log.info("getfunction command returned the error code");
}
}

};