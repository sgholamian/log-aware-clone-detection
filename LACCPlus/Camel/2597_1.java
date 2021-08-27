//,temp,IAM2Producer.java,516,554,temp,IAM2Producer.java,455,487
//,3
public class xxx {
    private void addUserToGroup(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof AddUserToGroupRequest) {
                AddUserToGroupResponse result;
                try {
                    result = iamClient.addUserToGroup((AddUserToGroupRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Add User To Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            AddUserToGroupRequest.Builder builder = AddUserToGroupRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.GROUP_NAME))) {
                String groupName = exchange.getIn().getHeader(IAM2Constants.GROUP_NAME, String.class);
                builder.groupName(groupName);
            } else {
                throw new IllegalArgumentException("Group Name must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.USERNAME))) {
                String userName = exchange.getIn().getHeader(IAM2Constants.USERNAME, String.class);
                builder.userName(userName);
            } else {
                throw new IllegalArgumentException("User Name must be specified");
            }
            AddUserToGroupResponse result;
            try {
                result = iamClient.addUserToGroup(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Add User To Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};