//,temp,IAM2Producer.java,489,514,temp,IAM2Producer.java,276,301
//,2
public class xxx {
    private void listGroups(IamClient iamClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof ListGroupsRequest) {
                ListGroupsResponse result;
                try {
                    result = iamClient.listGroups((ListGroupsRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("List Groups command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            ListGroupsResponse result;
            try {
                result = iamClient.listGroups();
            } catch (AwsServiceException ase) {
                LOG.trace("List Groups command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};