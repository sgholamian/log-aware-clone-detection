//,temp,KMS2Producer.java,175,207,temp,EKS2Producer.java,203,235
//,2
public class xxx {
    private void disableKey(KmsClient kmsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DisableKeyRequest) {
                DisableKeyResponse result;
                try {
                    result = kmsClient.disableKey((DisableKeyRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Disable Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DisableKeyRequest.Builder builder = DisableKeyRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(KMS2Constants.KEY_ID))) {
                String keyId = exchange.getIn().getHeader(KMS2Constants.KEY_ID, String.class);
                builder.keyId(keyId);
            } else {
                throw new IllegalArgumentException("Key Id must be specified");
            }
            DisableKeyResponse result;
            try {
                result = kmsClient.disableKey(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Disable Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};