//,temp,IAM2Producer.java,303,333,temp,MSK2Producer.java,98,128
//,2
public class xxx {
    private void createAccessKey(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateAccessKeyRequest) {
                CreateAccessKeyResponse result;
                try {
                    result = iamClient.createAccessKey((CreateAccessKeyRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create Access Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            CreateAccessKeyRequest.Builder builder = CreateAccessKeyRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.USERNAME))) {
                String userName = exchange.getIn().getHeader(IAM2Constants.USERNAME, String.class);
                builder.userName(userName);
            }
            CreateAccessKeyResponse result;
            try {
                result = iamClient.createAccessKey(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Create Access Key command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};