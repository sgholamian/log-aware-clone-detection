//,temp,XARecoveryBrokerTest.java,1249,1339,temp,XARecoveryBrokerTest.java,184,269
//,3
public class xxx {
    public void testPreparedTransactionRecoveredOnRestart() throws Exception {

        ActiveMQDestination destination = createDestination();

        // Setup the producer and send the message.
        StubConnection connection = createConnection();
        ConnectionInfo connectionInfo = createConnectionInfo();
        SessionInfo sessionInfo = createSessionInfo(connectionInfo);
        ProducerInfo producerInfo = createProducerInfo(sessionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);
        connection.send(producerInfo);
        ConsumerInfo consumerInfo = createConsumerInfo(sessionInfo, destination);
        connection.send(consumerInfo);

        // Prepare 4 message sends.
        for (int i = 0; i < 4; i++) {
            // Begin the transaction.
            XATransactionId txid = createXATransaction(sessionInfo);
            connection.send(createBeginTransaction(connectionInfo, txid));

            Message message = createMessage(producerInfo, destination);
            message.setPersistent(true);
            message.setTransactionId(txid);
            connection.send(message);

            // Prepare
            connection.send(createPrepareTransaction(connectionInfo, txid));
        }

        // Since prepared but not committed.. they should not get delivered.
        assertNull(receiveMessage(connection));
        assertNoMessagesLeft(connection);
        connection.request(closeConnectionInfo(connectionInfo));

        // restart the broker.
        restartBroker();

        // Setup the consumer and try receive the message.
        connection = createConnection();
        connectionInfo = createConnectionInfo();
        sessionInfo = createSessionInfo(connectionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);
        consumerInfo = createConsumerInfo(sessionInfo, destination);
        connection.send(consumerInfo);

        // Since prepared but not committed.. they should not get delivered.
        assertNull(receiveMessage(connection));
        assertNoMessagesLeft(connection);

        Response response = connection.request(new TransactionInfo(connectionInfo.getConnectionId(), null, TransactionInfo.RECOVER));
        assertNotNull(response);
        DataArrayResponse dar = (DataArrayResponse)response;
        assertEquals(4, dar.getData().length);

        // ensure we can close a connection with prepared transactions
        connection.request(closeConnectionInfo(connectionInfo));

        // open again  to deliver outcome
        connection = createConnection();
        connectionInfo = createConnectionInfo();
        sessionInfo = createSessionInfo(connectionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);
        consumerInfo = createConsumerInfo(sessionInfo, destination);
        connection.send(consumerInfo);

        // Commit the prepared transactions.
        for (int i = 0; i < dar.getData().length; i++) {
            TransactionId transactionId = (TransactionId) dar.getData()[i];
            LOG.info("commit: " + transactionId);
            connection.request(createCommitTransaction2Phase(connectionInfo, transactionId));
        }

        // We should get the committed transactions.
        final int countToReceive = expectedMessageCount(4, destination);
        for (int i = 0; i < countToReceive ; i++) {
            Message m = receiveMessage(connection, TimeUnit.SECONDS.toMillis(10));
            LOG.info("received: " + m);
            assertNotNull("Got non null message: " + i, m);
        }

        assertNoMessagesLeft(connection);
        assertEmptyDLQ();
    }

};