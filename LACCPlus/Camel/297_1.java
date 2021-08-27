//,temp,AWS2EC2Producer.java,306,342,temp,AWS2EC2Producer.java,268,304
//,2
public class xxx {
    @SuppressWarnings("unchecked")
    private void terminateInstances(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof TerminateInstancesRequest) {
                TerminateInstancesResponse result;
                try {
                    result = ec2Client.terminateInstances((TerminateInstancesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Terminate Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                LOG.trace("Terminating instances with Ids [{}] ", ((TerminateInstancesRequest) payload).instanceIds());
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            TerminateInstancesRequest.Builder builder = TerminateInstancesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            } else {
                throw new IllegalArgumentException("Instances Ids must be specified");
            }
            TerminateInstancesResponse result;
            try {
                result = ec2Client.terminateInstances(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Terminate Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            LOG.trace("Terminating instances with Ids [{}] ", Arrays.toString(instanceIds.toArray()));
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};