//,temp,Sqs2Endpoint.java,296,341,temp,Sqs2Endpoint.java,226,294
//,3
public class xxx {
    protected void createQueue(SqsClient client) throws IOException {
        if (queueExists(client)) {
            return;
        }

        LOG.trace("Creating the a queue named '{}'", configuration.getQueueName());

        // creates a new queue, or returns the URL of an existing one
        CreateQueueRequest.Builder request = CreateQueueRequest.builder().queueName(configuration.getQueueName());
        Map<QueueAttributeName, String> attributes = new HashMap<QueueAttributeName, String>();
        if (getConfiguration().isFifoQueue()) {
            attributes.put(QueueAttributeName.FIFO_QUEUE, String.valueOf(true));
            boolean useContentBasedDeduplication
                    = getConfiguration().getMessageDeduplicationIdStrategy() instanceof NullMessageDeduplicationIdStrategy;
            attributes.put(QueueAttributeName.CONTENT_BASED_DEDUPLICATION, String.valueOf(useContentBasedDeduplication));
        }
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
        LOG.trace("Trying to create queue [{}] with request [{}]...", configuration.getQueueName(), request);
        request.attributes(attributes);

        try {
            CreateQueueResponse queueResult = client.createQueue(request.build());
            queueUrl = queueResult.queueUrl();
        } catch (SqsException e) {
            if (queueExists(client)) {
                LOG.warn("The queue may have been created since last check and could not be created");
                LOG.debug("AWS SDK error preventing queue creation: {}", e.getMessage(), e);
            } else {
                throw e;
            }
        }

        LOG.trace("Queue created and available at: {}", queueUrl);
    }

};