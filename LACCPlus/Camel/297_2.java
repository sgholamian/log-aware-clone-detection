//,temp,AWS2EC2Producer.java,306,342,temp,AWS2EC2Producer.java,268,304
//,2
public class xxx {
    @SuppressWarnings("unchecked")
    private void stopInstances(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof StopInstancesRequest) {
                StopInstancesResponse result;
                try {
                    result = ec2Client.stopInstances((StopInstancesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Stop Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                LOG.trace("Stopping instances with Ids [{}] ", ((StopInstancesRequest) payload).instanceIds());
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            StopInstancesRequest.Builder builder = StopInstancesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            } else {
                throw new IllegalArgumentException("Instances Ids must be specified");
            }
            StopInstancesResponse result;
            try {
                result = ec2Client.stopInstances(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Stop Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            LOG.trace("Stopping instances with Ids [{}] ", Arrays.toString(instanceIds.toArray()));
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};