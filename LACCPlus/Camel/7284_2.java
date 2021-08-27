//,temp,IAM2Producer.java,174,206,temp,SecretsManagerProducer.java,125,147
//,3
public class xxx {
    private void listSecrets(SecretsManagerClient secretsManagerClient, Exchange exchange)
            throws InvalidPayloadException {
        ListSecretsRequest request = null;
        ListSecretsResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(ListSecretsRequest.class);
        } else {
            Builder builder = ListSecretsRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.MAX_RESULTS))) {
                int maxRes = exchange.getIn().getHeader(SecretsManagerConstants.MAX_RESULTS, Integer.class);
                builder.maxResults(maxRes);
            }
            request = builder.build();
        }
        try {
            result = secretsManagerClient.listSecrets(request);
        } catch (AwsServiceException ase) {
            LOG.trace("List Secrets command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};