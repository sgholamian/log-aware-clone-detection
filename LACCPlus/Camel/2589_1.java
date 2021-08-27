//,temp,IAM2Producer.java,556,594,temp,SecretsManagerProducer.java,337,372
//,3
public class xxx {
    private void removeUserFromGroup(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof RemoveUserFromGroupRequest) {
                RemoveUserFromGroupResponse result;
                try {
                    result = iamClient.removeUserFromGroup((RemoveUserFromGroupRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Remove User From Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            RemoveUserFromGroupRequest.Builder builder = RemoveUserFromGroupRequest.builder();
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
            RemoveUserFromGroupResponse result;
            try {
                result = iamClient.removeUserFromGroup(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Remove User From Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};