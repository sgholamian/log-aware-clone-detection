//,temp,IAM2Producer.java,242,274,temp,AWS2EC2Producer.java,378,411
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void describeInstancesStatus(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeInstanceStatusRequest) {
                DescribeInstanceStatusResponse result;
                try {
                    result = ec2Client.describeInstanceStatus((DescribeInstanceStatusRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Describe Instances Status command returned the error code {}",
                            ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeInstanceStatusRequest.Builder builder = DescribeInstanceStatusRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            }
            DescribeInstanceStatusResponse result;
            try {
                result = ec2Client.describeInstanceStatus(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Describe Instances Status command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};