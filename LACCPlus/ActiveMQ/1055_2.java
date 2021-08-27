//,temp,XARecoveryBrokerTest.java,1509,1585,temp,XARecoveryBrokerTest.java,1425,1503
//,3
public class xxx {
    public void testTopicPersistentPreparedAcksUnavailableTillRollback() throws Exception {

        ActiveMQDestination destination = new ActiveMQTopic("TryTopic");

        // Setup the producer and send the message.
        StubConnection connection = createConnection();
        ConnectionInfo connectionInfo = createConnectionInfo();
        connectionInfo.setClientId("durable");
        SessionInfo sessionInfo = createSessionInfo(connectionInfo);
        ProducerInfo producerInfo = createProducerInfo(sessionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);
        connection.send(producerInfo);

        // setup durable subs
        ConsumerInfo consumerInfo = createConsumerInfo(sessionInfo, destination);
        consumerInfo.setSubscriptionName("durable");
        connection.send(consumerInfo);

        int numMessages = 4;
        for (int i = 0; i < numMessages; i++) {
            Message message = createMessage(producerInfo, destination);
            message.setPersistent(true);
            connection.send(message);
        }

        // Begin the transaction.
        XATransactionId txid = createXATransaction(sessionInfo);
        connection.send(createBeginTransaction(connectionInfo, txid));

        Message message = null;
        for (int i = 0; i < numMessages; i++) {
            message = receiveMessage(connection);
            assertNotNull(message);
        }

        // one ack with last received, mimic a beforeEnd synchronization
        MessageAck ack = createAck(consumerInfo, message, numMessages, MessageAck.STANDARD_ACK_TYPE);
        ack.setTransactionId(txid);
        connection.send(ack);

        connection.request(createEndTransaction(connectionInfo, txid));
        connection.request(createPrepareTransaction(connectionInfo, txid));

        // reconnect, verify perpared acks unavailable
        connection.request(closeConnectionInfo(connectionInfo));

        LOG.info("new consumer for *no* redelivery");

        connectionInfo = createConnectionInfo();
        connectionInfo.setClientId("durable");
        sessionInfo = createSessionInfo(connectionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);

        // setup durable subs
        consumerInfo = createConsumerInfo(sessionInfo, destination);
        consumerInfo.setSubscriptionName("durable");
        connection.send(consumerInfo);

        message = receiveMessage(connection, 2000);
        assertNull("unexpected non null", message);

        // rollback original tx
        connection.request(createRollbackTransaction(connectionInfo, txid));

        // verify receive after rollback
        for (int i = 0; i < numMessages; i++) {
            message = receiveMessage(connection);
            assertNotNull("unexpected null on:" + i, message);
        }

        // unsubscribe
        connection.request(consumerInfo.createRemoveCommand());
        RemoveSubscriptionInfo removeSubscriptionInfo = new RemoveSubscriptionInfo();
        removeSubscriptionInfo.setClientId(connectionInfo.getClientId());
        removeSubscriptionInfo.setSubscriptionName(consumerInfo.getSubscriptionName());
        connection.request(removeSubscriptionInfo);
    }

};