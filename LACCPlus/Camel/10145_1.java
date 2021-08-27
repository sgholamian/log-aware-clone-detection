//,temp,Sqs2Endpoint.java,296,341,temp,Sqs2Endpoint.java,226,294
//,3
public class xxx {
    private void updateQueueAttributes(SqsClient client) throws IOException {
        SetQueueAttributesRequest.Builder request = SetQueueAttributesRequest.builder().queueUrl(queueUrl);
        Map<QueueAttributeName, String> attributes = new HashMap<QueueAttributeName, String>();
        if (getConfiguration().getDefaultVisibilityTimeout() != null) {
            attributes.put(QueueAttributeName.VISIBILITY_TIMEOUT,
                    String.valueOf(getConfiguration().getDefaultVisibilityTimeout()));
        }
        if (getConfiguration().getMaximumMessageSize() != null) {
            attributes.put(QueueAttributeName.MAXIMUM_MESSAGE_SIZE, String.valueOf(getConfiguration().getMaximumMessageSize()));
        }
        if (getConfiguration().getMessageRetentionPeriod() != null) {
            attributes.put(QueueAttributeName.MESSAGE_RETENTION_PERIOD,
                    String.valueOf(getConfiguration().getMessageRetentionPeriod()));
        }
        if (getConfiguration().getPolicy() != null) {
            InputStream s = ResourceHelper.resolveMandatoryResourceAsInputStream(this.getCamelContext(),
                    getConfiguration().getPolicy());
            String policy = IOUtils.toString(s, Charset.defaultCharset());
            attributes.put(QueueAttributeName.POLICY, policy);
        }
        if (getConfiguration().getReceiveMessageWaitTimeSeconds() != null) {
            attributes.put(QueueAttributeName.RECEIVE_MESSAGE_WAIT_TIME_SECONDS,
                    String.valueOf(getConfiguration().getReceiveMessageWaitTimeSeconds()));
        }
        if (getConfiguration().getDelaySeconds() != null && getConfiguration().isDelayQueue()) {
            attributes.put(QueueAttributeName.DELAY_SECONDS, String.valueOf(getConfiguration().getDelaySeconds()));
        }
        if (getConfiguration().getRedrivePolicy() != null) {
            attributes.put(QueueAttributeName.REDRIVE_POLICY, getConfiguration().getRedrivePolicy());
        }
        if (getConfiguration().isServerSideEncryptionEnabled()) {
            if (getConfiguration().getKmsMasterKeyId() != null) {
                attributes.put(QueueAttributeName.KMS_MASTER_KEY_ID, getConfiguration().getKmsMasterKeyId());
            }
            if (getConfiguration().getKmsDataKeyReusePeriodSeconds() != null) {
                attributes.put(QueueAttributeName.KMS_DATA_KEY_REUSE_PERIOD_SECONDS,
                        String.valueOf(getConfiguration().getKmsDataKeyReusePeriodSeconds()));
            }
        }
        if (!attributes.isEmpty()) {
            request.attributes(attributes);
            LOG.trace("Updating queue '{}' with the provided queue attributes...", configuration.getQueueName());
            client.setQueueAttributes(request.build());
            LOG.trace("Queue '{}' updated and available at {}'", configuration.getQueueName(), queueUrl);
        }
    }

};