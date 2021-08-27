//,temp,Lambda2Producer.java,505,535,temp,Lambda2Producer.java,406,433
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void tagResource(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        TagResourceRequest request = null;
        TagResourceResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(TagResourceRequest.class);
        } else {
            TagResourceRequest.Builder builder = TagResourceRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.RESOURCE_ARN))) {
                String resource = exchange.getIn().getHeader(Lambda2Constants.RESOURCE_ARN, String.class);
                builder.resource(resource);
            } else {
                throw new IllegalArgumentException("The resource ARN must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.RESOURCE_TAGS))) {
                Map<String, String> tags = exchange.getIn().getHeader(Lambda2Constants.RESOURCE_TAGS, Map.class);
                builder.tags(tags);
            } else {
                throw new IllegalArgumentException("The tags must be specified");
            }
            request = builder.build();
        }
        try {
            result = lambdaClient.tagResource(request);
        } catch (AwsServiceException ase) {
            LOG.trace("listTags command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};