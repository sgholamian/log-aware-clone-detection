//,temp,IAM2Producer.java,174,206,temp,SecretsManagerProducer.java,125,147
//,3
public class xxx {
    private void createUser(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateUserRequest) {
                CreateUserResponse result;
                try {
                    result = iamClient.createUser((CreateUserRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create user command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            CreateUserRequest.Builder builder = CreateUserRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.USERNAME))) {
                String userName = exchange.getIn().getHeader(IAM2Constants.USERNAME, String.class);
                builder.userName(userName);
            } else {
                throw new IllegalArgumentException("User Name must be specified");
            }
            CreateUserResponse result;
            try {
                result = iamClient.createUser(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Create user command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};