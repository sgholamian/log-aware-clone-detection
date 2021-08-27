//,temp,SecretsManagerProducer.java,270,299,temp,SecretsManagerProducer.java,149,183
//,3
public class xxx {
    private void rotateSecret(SecretsManagerClient secretsManagerClient, Exchange exchange)
            throws InvalidPayloadException {
        RotateSecretRequest request = null;
        RotateSecretResponse result;
        if (getConfiguration().isPojoRequest()) {
            request = exchange.getIn().getMandatoryBody(RotateSecretRequest.class);
        } else {
            RotateSecretRequest.Builder builder = RotateSecretRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID))) {
                String secretId = exchange.getIn().getHeader(SecretsManagerConstants.SECRET_ID, String.class);
                builder.secretId(secretId);
            } else {
                throw new IllegalArgumentException("Secret Id must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(SecretsManagerConstants.LAMBDA_ROTATION_FUNCTION_ARN))) {
                String lambdaRotationArn
                        = exchange.getIn().getHeader(SecretsManagerConstants.LAMBDA_ROTATION_FUNCTION_ARN, String.class);
                builder.rotationLambdaARN(lambdaRotationArn);
            }
            request = builder.build();
        }
        try {
            result = secretsManagerClient.rotateSecret(request);
        } catch (AwsServiceException ase) {
            LOG.trace("Rotate Secret value command returned the error code {}", ase.awsErrorDetails().errorCode());
            throw ase;
        }
        Message message = getMessageForResponse(exchange);
        message.setBody(result);
    }

};