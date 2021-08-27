//,temp,PushTopicHelper.java,200,249,temp,PushTopicHelper.java,146,198
//,3
public class xxx {
    private void createTopic() throws CamelException {
        final PushTopic topic = new PushTopic();
        topic.setName(topicName);
        topic.setApiVersion(Double.valueOf(config.getApiVersion()));
        topic.setQuery(config.getSObjectQuery());
        topic.setDescription("Topic created by Camel Salesforce component");
        topic.setNotifyForFields(config.getNotifyForFields());
        if (preApi29) {
            topic.setNotifyForOperations(config.getNotifyForOperations());
        } else {
            topic.setNotifyForOperationCreate(config.getNotifyForOperationCreate());
            topic.setNotifyForOperationDelete(config.getNotifyForOperationDelete());
            topic.setNotifyForOperationUndelete(config.getNotifyForOperationUndelete());
            topic.setNotifyForOperationUpdate(config.getNotifyForOperationUpdate());
        }

        LOG.info("Creating Topic {}: {}", topicName, topic);
        final SyncResponseCallback callback = new SyncResponseCallback();
        try {
            restClient.createSObject(PUSH_TOPIC_OBJECT_NAME, new ByteArrayInputStream(OBJECT_MAPPER.writeValueAsBytes(topic)),
                    Collections.emptyMap(), callback);

            if (!callback.await(API_TIMEOUT, TimeUnit.SECONDS)) {
                throw new SalesforceException("API call timeout!", null);
            }
            final SalesforceException callbackException = callback.getException();
            if (callbackException != null) {
                throw callbackException;
            }

            CreateSObjectResult result = OBJECT_MAPPER.readValue(callback.getResponse(), CreateSObjectResult.class);
            if (!result.getSuccess()) {
                final SalesforceException salesforceException
                        = new SalesforceException(result.getErrors(), HttpStatus.BAD_REQUEST_400);
                throw new CamelException(
                        String.format("Error creating Topic %s: %s", topicName, result.getErrors()), salesforceException);
            }
        } catch (SalesforceException e) {
            throw new CamelException(String.format("Error creating Topic %s: %s", topicName, e.getMessage()), e);
        } catch (IOException e) {
            throw new CamelException(String.format("Un-marshaling error creating Topic %s: %s", topicName, e.getMessage()), e);
        } catch (InterruptedException e) {
            throw new CamelException(String.format("Un-marshaling error creating Topic %s: %s", topicName, e.getMessage()), e);
        } finally {
            if (callback.getResponse() != null) {
                try {
                    callback.getResponse().close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

};