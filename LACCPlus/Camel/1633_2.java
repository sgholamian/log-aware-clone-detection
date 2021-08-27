//,temp,STS2Producer.java,163,196,temp,STS2Producer.java,93,132
//,3
public class xxx {
    private void assumeRole(StsClient stsClient, Exchange exchange) throws InvalidPayloadException {
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof AssumeRoleRequest) {
                AssumeRoleResponse result;
                try {
                    AssumeRoleRequest request = (AssumeRoleRequest) payload;
                    result = stsClient.assumeRole(request);
                } catch (AwsServiceException ase) {
                    LOG.trace("Assume Role command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            Builder builder = AssumeRoleRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(STS2Constants.ROLE_ARN))) {
                String roleArn = exchange.getIn().getHeader(STS2Constants.ROLE_ARN, String.class);
                builder.roleArn(roleArn);
            } else {
                throw new IllegalArgumentException("Role ARN needs to be specified for assumeRole operation");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(STS2Constants.ROLE_SESSION_NAME))) {
                String roleSessionName = exchange.getIn().getHeader(STS2Constants.ROLE_SESSION_NAME, String.class);
                builder.roleSessionName(roleSessionName);
            } else {
                throw new IllegalArgumentException("Role Session Name needs to be specified for assumeRole operation");
            }
            AssumeRoleResponse result;
            try {
                result = stsClient.assumeRole(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Assume Role command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};