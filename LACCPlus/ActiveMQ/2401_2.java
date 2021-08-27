//,temp,XARecoveryBrokerTest.java,1591,1677,temp,XARecoveryBrokerTest.java,1345,1418
//,3
public class xxx {
    public void testTopicPersistentPreparedAcksAvailableAfterRollback() throws Exception {

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

        connection.request(createPrepareTransaction(connectionInfo, txid));

        // rollback so we get redelivery
        connection.request(createRollbackTransaction(connectionInfo, txid));

        LOG.info("new consumer/tx for redelivery");
        connection.request(closeConnectionInfo(connectionInfo));

        connectionInfo = createConnectionInfo();
        connectionInfo.setClientId("durable");
        sessionInfo = createSessionInfo(connectionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);

        // setup durable subs
        consumerInfo = createConsumerInfo(sessionInfo, destination);
        consumerInfo.setSubscriptionName("durable");
        connection.send(consumerInfo);

        txid = createXATransaction(sessionInfo);
        connection.send(createBeginTransaction(connectionInfo, txid));

        for (int i = 0; i < numMessages; i++) {
            message = receiveMessage(connection);
            assertNotNull("unexpected null on:" + i, message);
        }
        ack = createAck(consumerInfo, message, numMessages, MessageAck.STANDARD_ACK_TYPE);
        ack.setTransactionId(txid);
        connection.send(ack);

        // Commit
        connection.request(createCommitTransaction1Phase(connectionInfo, txid));
    }

};