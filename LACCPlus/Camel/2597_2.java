//,temp,IAM2Producer.java,516,554,temp,IAM2Producer.java,455,487
//,3
public class xxx {
    private void deleteGroup(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateGroupRequest) {
                DeleteGroupResponse result;
                try {
                    result = iamClient.deleteGroup((DeleteGroupRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Delete Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DeleteGroupRequest.Builder builder = DeleteGroupRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(IAM2Constants.GROUP_NAME))) {
                String groupName = exchange.getIn().getHeader(IAM2Constants.GROUP_NAME, String.class);
                builder.groupName(groupName);
            } else {
                throw new IllegalArgumentException("Group Name must be specified");
            }
            DeleteGroupResponse result;
            try {
                result = iamClient.deleteGroup(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Delete Group command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};