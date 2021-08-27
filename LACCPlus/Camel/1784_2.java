//,temp,Lambda2Producer.java,649,672,temp,Lambda2Producer.java,480,503
//,3
public class xxx {
    private void listTags(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        ListTagsRequest request = null;
        ListTagsResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(ListTagsRequest.class);
        } else {
            ListTagsRequest.Builder builder = ListTagsRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.RESOURCE_ARN))) {
                String resource = exchange.getIn().getHeader(Lambda2Constants.RESOURCE_ARN, String.class);
                builder.resource(resource);
            } else {
                throw new IllegalArgumentException("The resource ARN must be specified");
            }
            request = builder.build();
        }
        try {
            result = lambdaClient.listTags(request);
        } catch (AwsServiceException ase) {
            LOG.trace("listTags command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};