//,temp,Lambda2Producer.java,649,672,temp,Lambda2Producer.java,480,503
//,3
public class xxx {
    private void deleteAlias(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        DeleteAliasRequest request = null;
        DeleteAliasResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(DeleteAliasRequest.class);
        } else {
            DeleteAliasRequest.Builder builder = DeleteAliasRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            String aliasName = exchange.getIn().getHeader(Lambda2Constants.FUNCTION_ALIAS_NAME, String.class);
            if (ObjectHelper.isEmpty(aliasName)) {
                throw new IllegalArgumentException("Function alias must be specified to delete an alias");
            }
            builder.name(aliasName);
            request = builder.build();
        }
        try {
            result = lambdaClient.deleteAlias(request);
        } catch (AwsServiceException ase) {
            LOG.trace("deleteAlias command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};