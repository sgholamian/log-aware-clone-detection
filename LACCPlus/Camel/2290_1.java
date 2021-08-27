//,temp,IAM2Producer.java,208,240,temp,AWS2EC2Producer.java,445,481
//,3
public class xxx {
    private void deleteUser(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DeleteUserRequest) {
                DeleteUserResponse result;
                try {
                    result = iamClient.deleteUser((DeleteUserRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete user command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteUserRequest.Builder builder = DeleteUserRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.USERNAME))) {
                String userName = exchange.getIn().getHeader(IAM2Constants.USERNAME, String.class);
                builder.userName(userName);
            } else {
                throw new IllegalArgumentException("User Name must be specified");
            }
            DeleteUserResponse result;
            try {
                result = iamClient.deleteUser(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete user command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};