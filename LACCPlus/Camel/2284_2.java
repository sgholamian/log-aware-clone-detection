//,temp,SecretsManagerProducer.java,270,299,temp,SecretsManagerProducer.java,149,183
//,3
public class xxx {
    private void createSecret(SecretsManagerClient secretsManagerClient, Exchange exchange)
            throws InvalidPayloadException {
        CreateSecretRequest request = null;
        CreateSecretResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(CreateSecretRequest.class);
        } else {
            CreateSecretRequest.Builder builder = CreateSecretRequest.builder();
            String payload = exchange.getIn().getMandatoryBody(String.class);
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_NAME))) {
                String secretName = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_NAME, String.class);
                builder.name(secretName);
            } else {
                throw new IllegalArgumentException("Secret Name must be specified");
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
            result = secretsManagerClient.createSecret(request);
        } catch (AwsServiceException ase) {
            LOG.trace("Create Secret command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};