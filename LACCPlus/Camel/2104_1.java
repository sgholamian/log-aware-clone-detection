//,temp,AWS2EC2Producer.java,483,519,temp,AWS2EC2Producer.java,344,376
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void unmonitorInstances(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof UnmonitorInstancesRequest) {
                UnmonitorInstancesResponse result;
                try {
                    result = ec2Client.unmonitorInstances((UnmonitorInstancesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Unmonitor Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                LOG.trace("Stop Monitoring instances with Ids [{}] ", ((UnmonitorInstancesRequest) payload).instanceIds());
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            UnmonitorInstancesRequest.Builder builder = UnmonitorInstancesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            } else {
                throw new IllegalArgumentException("Instances Ids must be specified");
            }
            UnmonitorInstancesResponse result;
            try {
                result = ec2Client.unmonitorInstances(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Unmonitor Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            LOG.trace("Stop Monitoring instances with Ids [{}] ", Arrays.toString(instanceIds.toArray()));
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};