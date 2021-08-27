//,temp,PushTopicHelper.java,200,249,temp,PushTopicHelper.java,146,198
//,3
public class xxx {
    private void updateTopic(String topicId) throws CamelException {
        final String query = config.getSObjectQuery();
        LOG.info("Updating Topic {} with Query [{}]", topicName, query);

        final SyncResponseCallback callback = new SyncResponseCallback();
        try {
            // update the query, notifyForFields and notifyForOperations fields
            final PushTopic topic = new PushTopic();
            topic.setQuery(query);
            topic.setNotifyForFields(config.getNotifyForFields());
            if (preApi29) {
                topic.setNotifyForOperations(config.getNotifyForOperations());
            } else {
                topic.setNotifyForOperationCreate(config.getNotifyForOperationCreate());
                topic.setNotifyForOperationDelete(config.getNotifyForOperationDelete());
                topic.setNotifyForOperationUndelete(config.getNotifyForOperationUndelete());
                topic.setNotifyForOperationUpdate(config.getNotifyForOperationUpdate());
            }

            restClient.updateSObject("PushTopic", topicId, new ByteArrayInputStream(OBJECT_MAPPER.writeValueAsBytes(topic)),
                    Collections.emptyMap(), callback);

            if (!callback.await(API_TIMEOUT, TimeUnit.SECONDS)) {
                throw new SalesforceException("API call timeout!", null);
            }
            final SalesforceException callbackException = callback.getException();
            if (callbackException != null) {
                throw callbackException;
            }

        } catch (SalesforceException e) {
            throw new CamelException(
                    String.format("Error updating topic %s with query [%s] : %s", topicName, query, e.getMessage()), e);
        } catch (InterruptedException e) {
            // reset interrupt status
            Thread.currentThread().interrupt();
            throw new CamelException(
                    String.format("Error updating topic %s with query [%s] : %s", topicName, query, e.getMessage()), e);
        } catch (IOException e) {
            throw new CamelException(
                    String.format("Error updating topic %s with query [%s] : %s", topicName, query, e.getMessage()), e);
        } finally {
            if (callback.getResponse() != null) {
                try {
                    callback.getResponse().close();
                } catch (IOException ignore) {
                }
            }
        }
    }

};