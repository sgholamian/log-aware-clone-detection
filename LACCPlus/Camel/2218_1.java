//,temp,SecretsManagerProducer.java,244,268,temp,Lambda2Producer.java,698,720
//,3
public class xxx {
    private void deleteSecret(SecretsManagerClient secretsManagerClient, Exchange exchange)
            throws InvalidPayloadException {
        DeleteSecretRequest request = null;
        DeleteSecretResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(DeleteSecretRequest.class);
        } else {
            DeleteSecretRequest.Builder builder = DeleteSecretRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID))) {
                String secretId = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID, String.class);
                builder.secretId(secretId);
            } else {
                throw new IllegalArgumentException("Secret Id must be specified");
            }
            request = builder.build();
        }
        try {
            result = secretsManagerClient.deleteSecret(request);
        } catch (AwsServiceException ase) {
            LOG.trace("Delete Secret value command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};