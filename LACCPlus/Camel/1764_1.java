//,temp,Lambda2Producer.java,435,458,temp,Lambda2Producer.java,154,173
//,3
public class xxx {
    private void deleteEventSourceMapping(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        DeleteEventSourceMappingRequest request = null;
        DeleteEventSourceMappingResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(DeleteEventSourceMappingRequest.class);
        } else {
            DeleteEventSourceMappingRequest.Builder builder = DeleteEventSourceMappingRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.EVENT_SOURCE_UUID))) {
                builder.uuid(exchange.getIn().getHeader(Lambda2Constants.EVENT_SOURCE_UUID, String.class));
            } else {
                throw new IllegalArgumentException("Event Source Arn must be specified");
            }
            request = builder.build();
        }

        try {
            result = lambdaClient.deleteEventSourceMapping(request);
        } catch (AwsServiceException ase) {
            LOG.trace("deleteEventSourceMapping command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};