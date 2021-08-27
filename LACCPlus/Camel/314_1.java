//,temp,IAM2Producer.java,417,453,temp,IAM2Producer.java,335,371
//,2
public class xxx {
    private void createGroup(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateGroupRequest) {
                CreateGroupResponse result;
                try {
                    result = iamClient.createGroup((CreateGroupRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            CreateGroupRequest.Builder builder = CreateGroupRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.GROUP_NAME))) {
                String groupName = exchange.getIn().getHeader(IAM2Constants.GROUP_NAME, String.class);
                builder.groupName(groupName);
            } else {
                throw new IllegalArgumentException("Group Name must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.GROUP_PATH))) {
                String groupPath = exchange.getIn().getHeader(IAM2Constants.GROUP_PATH, String.class);
                builder.path(groupPath);
            }
            CreateGroupResponse result;
            try {
                result = iamClient.createGroup(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Create Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};