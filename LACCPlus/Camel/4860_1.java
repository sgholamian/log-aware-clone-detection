//,temp,SecretsManagerProducer.java,301,335,temp,Lambda2Producer.java,617,647
//,3
public class xxx {
    private void updateSecret(SecretsManagerClient secretsManagerClient, Exchange exchange)
            throws InvalidPayloadException {
        UpdateSecretRequest request = null;
        UpdateSecretResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(UpdateSecretRequest.class);
        } else {
            UpdateSecretRequest.Builder builder = UpdateSecretRequest.builder();
            String payload = exchange.getIn().getMandatoryBody(String.class);
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID))) {
                String secretId = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID, String.class);
                builder.secretId(secretId);
            } else {
                throw new IllegalArgumentException("Secret Id must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_DESCRIPTION))) {
                String descr = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_DESCRIPTION, String.class);
                builder.description(descr);
            }
            if (getConfiguration().isBinaryPayload()) {
                builder.secretBinary(SdkBytes.fromUtf8String(Base64.getEncoder().encodeToString(payload.getBytes())));
            } else {
                builder.secretString((String) payload);
            }
            request = builder.build();
        }
        try {
            result = secretsManagerClient.updateSecret(request);
        } catch (AwsServiceException ase) {
            LOG.trace("Update Secret command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};