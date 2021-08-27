//,temp,Lambda2Producer.java,597,615,temp,Lambda2Producer.java,215,233
//,3
public class xxx {
    private void listVersions(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        ListVersionsByFunctionRequest request = null;
        ListVersionsByFunctionResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(ListVersionsByFunctionRequest.class);
        } else {
            ListVersionsByFunctionRequest.Builder builder = ListVersionsByFunctionRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            request = builder.build();
        }
        try {
            result = lambdaClient.listVersionsByFunction(request);
        } catch (AwsServiceException ase) {
            LOG.trace("publishVersion command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};