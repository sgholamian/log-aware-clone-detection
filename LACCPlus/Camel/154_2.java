//,temp,Lambda2Producer.java,460,478,temp,Lambda2Producer.java,175,194
//,2
public class xxx {
    private void deleteFunction(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        DeleteFunctionRequest request = null;
        DeleteFunctionResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(DeleteFunctionRequest.class);
        } else {
            DeleteFunctionRequest.Builder builder = DeleteFunctionRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            request = builder.build();
        }
        try {
            result = lambdaClient
                    .deleteFunction(request);
        } catch (AwsServiceException ase) {
            LOG.trace("deleteFunction command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};