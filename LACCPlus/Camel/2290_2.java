//,temp,IAM2Producer.java,208,240,temp,AWS2EC2Producer.java,445,481
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void monitorInstances(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof MonitorInstancesRequest) {
                MonitorInstancesResponse result;
                try {
                    result = ec2Client.monitorInstances((MonitorInstancesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Monitor Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                LOG.trace("Start Monitoring instances with Ids [{}] ", ((MonitorInstancesRequest) payload).instanceIds());
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            MonitorInstancesRequest.Builder builder = MonitorInstancesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            } else {
                throw new IllegalArgumentException("Instances Ids must be specified");
            }
            MonitorInstancesResponse result;
            try {
                result = ec2Client.monitorInstances(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Monitor Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            LOG.trace("Start Monitoring instances with Ids [{}] ", Arrays.toString(instanceIds.toArray()));
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};