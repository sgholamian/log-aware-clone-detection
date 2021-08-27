//,temp,IAM2Producer.java,373,415,temp,MSK2Producer.java,130,181
//,3
public class xxx {
    private void updateAccessKey(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof UpdateAccessKeyRequest) {
                UpdateAccessKeyResponse result;
                try {
                    result = iamClient.updateAccessKey((UpdateAccessKeyRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Update Access Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            UpdateAccessKeyRequest.Builder builder = UpdateAccessKeyRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.ACCESS_KEY_ID))) {
                String accessKeyId = exchange.getIn().getHeader(IAM2Constants.ACCESS_KEY_ID, String.class);
                builder.accessKeyId(accessKeyId);
            } else {
                throw new IllegalArgumentException("Key Id must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.ACCESS_KEY_STATUS))) {
                String status = exchange.getIn().getHeader(IAM2Constants.ACCESS_KEY_STATUS, String.class);
                builder.status(StatusType.fromValue(status));
            } else {
                throw new IllegalArgumentException("Access Key status must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.USERNAME))) {
                String userName = exchange.getIn().getHeader(IAM2Constants.USERNAME, String.class);
                builder.userName(userName);
            }
            UpdateAccessKeyResponse result;
            try {
                result = iamClient.updateAccessKey(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Update Access Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};