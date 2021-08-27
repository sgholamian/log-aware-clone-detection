//,temp,SecretsManagerProducer.java,301,335,temp,Lambda2Producer.java,617,647
//,3
public class xxx {
    private void createAlias(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        CreateAliasRequest request = null;
        CreateAliasResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(CreateAliasRequest.class);
        } else {
            CreateAliasRequest.Builder builder = CreateAliasRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            String version = exchange.getIn().getHeader(Lambda2Constants.FUNCTION_VERSION, String.class);
            String aliasName = exchange.getIn().getHeader(Lambda2Constants.FUNCTION_ALIAS_NAME, String.class);
            if (ObjectHelper.isEmpty(version) || ObjectHelper.isEmpty(aliasName)) {
                throw new IllegalArgumentException("Function Version and alias must be specified to create an alias");
            }
            builder.functionVersion(version);
            builder.name(aliasName);
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(Lambda2Constants.FUNCTION_ALIAS_DESCRIPTION))) {
                String aliasDescription
                        = exchange.getIn().getHeader(Lambda2Constants.FUNCTION_ALIAS_DESCRIPTION, String.class);
                builder.description(aliasDescription);
            }
            request = builder.build();
        }
        try {
            result = lambdaClient.createAlias(request);
        } catch (AwsServiceException ase) {
            LOG.trace("createAlias command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};