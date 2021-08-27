//,temp,Lambda2Producer.java,435,458,temp,Lambda2Producer.java,154,173
//,3
public class xxx {
    private void getFunction(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        GetFunctionRequest request = null;
        GetFunctionResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(GetFunctionRequest.class);
        } else {
            GetFunctionRequest.Builder builder = GetFunctionRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            request = builder.build();
        }
        try {
            result = lambdaClient
                    .getFunction(request);
        } catch (AwsServiceException ase) {
            LOG.trace("getFunction command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};