//,temp,KMS2Producer.java,247,279,temp,EKS2Producer.java,169,201
//,2
public class xxx {
    private void describeKey(KmsClient kmsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeKeyRequest) {
                DescribeKeyResponse result;
                try {
                    result = kmsClient.describeKey((DescribeKeyRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Describe Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeKeyRequest.Builder builder = DescribeKeyRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(KMS2Constants.KEY_ID))) {
                String keyId = exchange.getIn().getHeader(KMS2Constants.KEY_ID, String.class);
                builder.keyId(keyId);
            } else {
                throw new IllegalArgumentException("Key Id must be specified");
            }
            DescribeKeyResponse result;
            try {
                result = kmsClient.describeKey(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Describe Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};