//,temp,IAM2Producer.java,417,453,temp,IAM2Producer.java,335,371
//,2
public class xxx {
    private void deleteAccessKey(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DeleteAccessKeyRequest) {
                DeleteAccessKeyResponse result;
                try {
                    result = iamClient.deleteAccessKey((DeleteAccessKeyRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Access Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteAccessKeyRequest.Builder builder = DeleteAccessKeyRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.ACCESS_KEY_ID))) {
                String accessKeyId = exchange.getIn().getHeader(IAM2Constants.ACCESS_KEY_ID, String.class);
                builder.accessKeyId(accessKeyId);
            } else {
                throw new IllegalArgumentException("Key Id must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.USERNAME))) {
                String userName = exchange.getIn().getHeader(IAM2Constants.USERNAME, String.class);
                builder.userName(userName);
            }
            DeleteAccessKeyResponse result;
            try {
                result = iamClient.deleteAccessKey(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Access Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};