//,temp,Lambda2Producer.java,569,595,temp,EventbridgeProducer.java,214,251
//,3
public class xxx {
    private void publishVersion(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        PublishVersionRequest request = null;
        PublishVersionResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(PublishVersionRequest.class);
        } else {
            PublishVersionRequest.Builder builder = PublishVersionRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.VERSION_DESCRIPTION))) {
                String description = exchange.getIn().getHeader(Lambda2Constants.VERSION_DESCRIPTION, String.class);
                builder.description(description);
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.VERSION_REVISION_ID))) {
                String revisionId = exchange.getIn().getHeader(Lambda2Constants.VERSION_REVISION_ID, String.class);
                builder.revisionId(revisionId);
            }
            request = builder.build();
        }
        try {
            result = lambdaClient.publishVersion(request);
        } catch (AwsServiceException ase) {
            LOG.trace("publishVersion command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};