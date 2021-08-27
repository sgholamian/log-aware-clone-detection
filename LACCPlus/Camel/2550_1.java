//,temp,AWS2EC2Producer.java,521,565,temp,AWS2EC2Producer.java,413,443
//,3
public class xxx {
    @SuppressWarnings("unchecked")
    private void createTags(Ec2Client ec2Client, Exchange exchange) throws InvalidPayloadException {
        Collection<String> instanceIds;
        if (getConfiguration().isPojoRequest()) {
            Object payload = exchange.getIn().getMandatoryBody();
            if (payload instanceof CreateTagsRequest) {
                CreateTagsResponse result;
                try {
                    result = ec2Client.createTags((CreateTagsRequest) payload);
                } catch (AwsServiceException ase) {
                    LOG.trace("Create tags command returned the error code {}", ase.awsErrorDetails().errorCode());
                    throw ase;
                }
                LOG.trace("Created tags [{}] ", ((CreateTagsRequest) payload).tags());
                Message message = getMessageForResponse(exchange);
                message.setBody(result);
            }
        } else {
            Collection<Tag> tags;
            CreateTagsRequest.Builder builder = CreateTagsRequest.builder();
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS))) {
                instanceIds = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_IDS, Collection.class);
                builder.resources(instanceIds);
            } else {
                throw new IllegalArgumentException("Instances Ids must be specified");
            }
            if (ObjectHelper.isNotEmpty(exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_TAGS))) {
                tags = exchange.getIn().getHeader(AWS2EC2Constants.INSTANCES_TAGS, Collection.class);
                builder.tags(tags);
            } else {
                throw new IllegalArgumentException("Tags must be specified");
            }
            CreateTagsResponse result;
            try {
                result = ec2Client.createTags(builder.build());
            } catch (AwsServiceException ase) {
                LOG.trace("Create tags command returned the error code {}", ase.awsErrorDetails().errorCode());
                throw ase;
            }
            LOG.trace("Created tags [{}] on resources with Ids [{}] ", Arrays.toString(tags.toArray()),
                    Arrays.toString(instanceIds.toArray()));
            Message message = getMessageForResponse(exchange);
            message.setBody(result);
        }
    }

};