//,temp,IAM2Producer.java,242,274,temp,AWS2EC2Producer.java,378,411
//,3
public class xxx {
    private void getUser(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof GetUserRequest) {
                GetUserResponse result;
                try {
                    result = iamClient.getUser((GetUserRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("get user command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            GetUserRequest.Builder builder = GetUserRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.USERNAME))) {
                String userName = exchange.getIn().getHeader(IAM2Constants.USERNAME, String.class);
                builder.userName(userName);
            } else {
                throw new IllegalArgumentException("User Name must be specified");
            }
            GetUserResponse result;
            try {
                result = iamClient.getUser(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("get user command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};