//,temp,MSK2Producer.java,183,215,temp,KMS2Producer.java,281,313
//,2
public class xxx {
    private void enableKey(KmsClient kmsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof EnableKeyRequest) {
                EnableKeyResponse result;
                try {
                    result = kmsClient.enableKey((EnableKeyRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Enable Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            EnableKeyRequest.Builder builder = EnableKeyRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(KMS2Constants.KEY_ID))) {
                String keyId = exchange.getIn().getHeader(KMS2Constants.KEY_ID, String.class);
                builder.keyId(keyId);
            } else {
                throw new IllegalArgumentException("Key Id must be specified");
            }
            EnableKeyResponse result;
            try {
                result = kmsClient.enableKey(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Enable Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};