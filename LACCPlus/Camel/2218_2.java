//,temp,SecretsManagerProducer.java,244,268,temp,Lambda2Producer.java,698,720
//,3
public class xxx {
    private void listAliases(LambdaClient lambdaClient, Exchange exchange) throws InvalidPayloadException {
        ListAliasesRequest request = null;
        ListAliasesResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(ListAliasesRequest.class);
        } else {
            ListAliasesRequest.Builder builder = ListAliasesRequest.builder();
            builder.functionName(getEndpoint().getFunction());
            String version = exchange.getIn().getHeader(Lambda2Constants.FUNCTION_VERSION, String.class);
            if (ObjectHelper.isEmpty(version)) {
                throw new IllegalArgumentException("Function Version must be specified to list aliases for a function");
            }
            builder.functionVersion(version);
        }
        try {
            result = lambdaClient.listAliases(request);
        } catch (AwsServiceException ase) {
            LOG.trace("listAliases command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};