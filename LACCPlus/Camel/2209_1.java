//,temp,SecretsManagerProducer.java,218,242,temp,Lambda2Producer.java,674,696
//,3
public class xxx {
    private void describeSecret(SecretsManagerClient secretsManagerClient, Exchange exchange)
            throws InvalidPayloadException {
        DescribeSecretRequest request = null;
        DescribeSecretResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(DescribeSecretRequest.class);
        } else {
            DescribeSecretRequest.Builder builder = DescribeSecretRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID))) {
                String secretId = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID, String.class);
                builder.secretId(secretId);
            } else {
                throw new IllegalArgumentException("Secret Id must be specified");
            }
            request = builder.build();
        }
        try {
            result = secretsManagerClient.describeSecret(request);
        } catch (AwsServiceException ase) {
            LOG.trace("Describe Secret value command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};