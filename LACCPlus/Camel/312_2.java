//,temp,IAM2Producer.java,489,514,temp,IAM2Producer.java,276,301
//,2
public class xxx {
    private void listUsers(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListUsersRequest) {
                ListUsersResponse result;
                try {
                    result = iamClient.listUsers((ListUsersRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List users command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListUsersResponse result;
            try {
                result = iamClient.listUsers();
            } catch (AwsServiceException ase) {
                LOG.trace("List users command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};