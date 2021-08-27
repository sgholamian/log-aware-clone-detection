//,temp,Lambda2Producer.java,597,615,temp,Lambda2Producer.java,215,233
//,3
public class xxx {
    private void invokeFunction(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        InvokeRequest request = null;
        InvokeResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(InvokeRequest.class);
        } else {
            InvokeRequest.Builder builder = InvokeRequest.builder();
            request = builder.functionName(getEndpoint().getFunction())
                    .payload(SdkBytes.fromString(exchange.getIn().getBody(String.class), Charset.defaultCharset())).build();
        }
        try {
            result = lambdaClient.invoke(request);
        } catch (AwsServiceException ase) {
            LOG.trace("invokeFunction command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result.payload().asUtf8String());
    }

};