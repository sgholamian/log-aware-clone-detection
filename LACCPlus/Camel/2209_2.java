//,temp,SecretsManagerProducer.java,218,242,temp,Lambda2Producer.java,674,696
//,3
public class xxx {
    private void getAlias(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        GetAliasRequest request = null;
        GetAliasResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(GetAliasRequest.class);
        } else {
            GetAliasRequest.Builder builder = GetAliasRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            String aliasName = exchange.getIn().getHeader(Lambda2Constants.FUNCTION_ALIAS_NAME, String.class);
            if (ObjectHelper.isEmpty(aliasName)) {
                throw new IllegalArgumentException("Function alias must be specified to get an alias");
            }
            builder.name(aliasName);
        }
        try {
            result = lambdaClient.getAlias(request);
        } catch (AwsServiceException ase) {
            LOG.trace("getAlias command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};