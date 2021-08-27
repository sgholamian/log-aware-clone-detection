//,temp,Lambda2Producer.java,505,535,temp,Lambda2Producer.java,406,433
//,3
public class xxx {
    private void createEventSourceMapping(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        CreateEventSourceMappingRequest request = null;
        CreateEventSourceMappingResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(CreateEventSourceMappingRequest.class);
        } else {
            CreateEventSourceMappingRequest.Builder builder = CreateEventSourceMappingRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.EVENT_SOURCE_ARN))) {
                builder.eventSourceArn(exchange.getIn().getHeader(Lambda2Constants.EVENT_SOURCE_ARN, String.class));
            } else {
                throw new IllegalArgumentException("Event Source Arn must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.EVENT_SOURCE_BATCH_SIZE))) {
                Integer batchSize = exchange.getIn().getHeader(Lambda2Constants.EVENT_SOURCE_BATCH_SIZE, Integer.class);
                builder.batchSize(batchSize);
            }
            request = builder.build();
        }
        try {
            result = lambdaClient.createEventSourceMapping(request);
        } catch (AwsServiceException ase) {
            LOG.trace("createEventSourceMapping command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};