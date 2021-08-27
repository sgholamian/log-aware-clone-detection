//,temp,AWS2EC2Producer.java,483,519,temp,AWS2EC2Producer.java,344,376
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void describeInstances(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof DescribeInstancesRequest) {
                DescribeInstancesResponse result;
                try {
                    result = ec2Client.describeInstances((DescribeInstancesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Describe Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            DescribeInstancesRequest.Builder builder = DescribeInstancesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            }
            DescribeInstancesResponse result;
            try {
                result = ec2Client.describeInstances(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Describe Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};