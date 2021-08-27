//,temp,Lambda2Producer.java,460,478,temp,Lambda2Producer.java,175,194
//,2
public class xxx {
    private void listEventSourceMapping(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        ListEventSourceMappingsRequest request = null;
        ListEventSourceMappingsResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(ListEventSourceMappingsRequest.class);
        } else {
            ListEventSourceMappingsRequest.Builder builder = ListEventSourceMappingsRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            request = builder.build();
        }
        try {
            result = lambdaClient.listEventSourceMappings(request);
        } catch (AwsServiceException ase) {
            LOG.trace("listEventSourceMapping command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};