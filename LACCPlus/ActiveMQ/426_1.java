//,temp,XARecoveryBrokerTest.java,1249,1339,temp,XARecoveryBrokerTest.java,184,269
//,3
public class xxx {
    public void testTopicPersistentPreparedAcksAvailableAfterRestartAndRollback() throws Exception {

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

        // restart the broker.
        restartBroker();

        connection = createConnection();
        connectionInfo = createConnectionInfo();
        connectionInfo.setClientId("durable");
        connection.send(connectionInfo);

        // validate recovery
        TransactionInfo recoverInfo = new TransactionInfo(connectionInfo.getConnectionId(), null, TransactionInfo.RECOVER);
        DataArrayResponse dataArrayResponse = (DataArrayResponse)connection.request(recoverInfo);

        assertEquals("there is a prepared tx", 1, dataArrayResponse.getData().length);
        assertEquals("it matches", txid, dataArrayResponse.getData()[0]);

        sessionInfo = createSessionInfo(connectionInfo);
        connection.send(sessionInfo);
        consumerInfo = createConsumerInfo(sessionInfo, destination);
        consumerInfo.setSubscriptionName("durable");
        connection.send(consumerInfo);

        // no redelivery, exactly once semantics while prepared
        message = receiveMessage(connection);
        assertNull(message);
        assertNoMessagesLeft(connection);

        // rollback so we get redelivery
        connection.request(createRollbackTransaction(connectionInfo, txid));

        LOG.info("new tx for redelivery");
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

        // validate recovery complete
        dataArrayResponse = (DataArrayResponse)connection.request(recoverInfo);
        assertEquals("there are no prepared tx", 0, dataArrayResponse.getData().length);
    }

};