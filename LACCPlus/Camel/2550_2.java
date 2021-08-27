//,temp,AWS2EC2Producer.java,521,565,temp,AWS2EC2Producer.java,413,443
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void rebootInstances(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof RebootInstancesRequest) {
                try {
                    LOG.trace("Rebooting instances with Ids [{}] ", ((RebootInstancesRequest) payload).instanceIds());
                    ec2Client.rebootInstances((RebootInstancesRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Reboot Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
            }
        } else {
            RebootInstancesRequest.Builder builder = RebootInstancesRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.instanceIds(instanceIds);
            } else {
                throw new IllegalArgumentException("Instances Ids must be specified");
            }
            try {
                LOG.trace("Rebooting instances with Ids [{}] ", Arrays.toString(instanceIds.toArray()));
                ec2Client.rebootInstances(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Reboot Instances command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
        }
    }

};