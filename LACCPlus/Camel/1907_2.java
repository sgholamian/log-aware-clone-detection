//,temp,MSK2Producer.java,217,249,temp,KMS2Producer.java,143,173
//,3
public class xxx {
    private void createKey(KmsClient kmsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateKeyRequest) {
                CreateKeyResponse result;
                try {
                    result = kmsClient.createKey((CreateKeyRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            CreateKeyRequest.Builder builder = CreateKeyRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(KMS2Constants.DESCRIPTION))) {
                String description = exchange.getIn().getHeader(KMS2Constants.DESCRIPTION, String.class);
                builder.description(description);
            }
            CreateKeyResponse result;
            try {
                result = kmsClient.createKey(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Create Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};