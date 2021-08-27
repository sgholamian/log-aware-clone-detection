//,temp,Lambda2Producer.java,537,567,temp,Lambda2Producer.java,372,404
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void untagResource(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        UntagResourceRequest request = null;
        UntagResourceResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(UntagResourceRequest.class);
        } else {
            UntagResourceRequest.Builder builder = UntagResourceRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.RESOURCE_ARN))) {
                String resource = exchange.getIn().getHeader(Lambda2Constants.RESOURCE_ARN, String.class);
                builder.resource(resource);
            } else {
                throw new IllegalArgumentException("The resource ARN must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.RESOURCE_TAG_KEYS))) {
                List<String> tagKeys = exchange.getIn().getHeader(Lambda2Constants.RESOURCE_TAG_KEYS, List.class);
                builder.tagKeys(tagKeys);
            } else {
                throw new IllegalArgumentException("The tag keys must be specified");
            }
            request = builder.build();
        }
        try {
            result = lambdaClient.untagResource(request);
        } catch (AwsServiceException ase) {
            LOG.trace("untagResource command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};