//,temp,AWS2EC2Producer.java,230,266,temp,ECS2Producer.java,132,164
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void startInstances(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof StartInstancesRequest) {
                StartInstancesResponse result;
                try {
                    result = ec2Client.startInstances((StartInstancesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Start Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                LOG.trace("Starting instances with Ids [{}] ", ((StartInstancesRequest) payload).instanceIds());
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            StartInstancesRequest.Builder builder = StartInstancesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            } else {
                throw new IllegalArgumentException("Instances Ids must be specified");
            }
            StartInstancesResponse result;
            try {
                result = ec2Client.startInstances(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Start Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            LOG.trace("Starting instances with Ids [{}] ", Arrays.toString(instanceIds.toArray()));
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};