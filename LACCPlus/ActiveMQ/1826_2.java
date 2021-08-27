//,temp,KahaDBXARecoveryBrokerTest.java,123,187,temp,KahaDBXARecoveryBrokerTest.java,55,121
//,3
public class xxx {
    public void testPreparedTransactionRecoveredPurgeCommitOnRestart() throws Exception {

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
        stopBroker();
        if (broker.getPersistenceAdapter() instanceof KahaDBPersistenceAdapter) {
            KahaDBPersistenceAdapter adapter = (KahaDBPersistenceAdapter)broker.getPersistenceAdapter();
            adapter.setPurgeRecoveredXATransactionStrategy("COMMIT");
            LOG.info("Setting purgeRecoveredXATransactions to true on the KahaDBPersistenceAdapter");
        }
        broker.start();

        // Setup the consumer and try receive the message.
        connection = createConnection();
        connectionInfo = createConnectionInfo();
        sessionInfo = createSessionInfo(connectionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);
        consumerInfo = createConsumerInfo(sessionInfo, destination);
        connection.send(consumerInfo);

        // Since committed ... they should get delivered.
        for (int i = 0; i < 4; i++) {
            assertNotNull(receiveMessage(connection));
        }
        assertNoMessagesLeft(connection);

        Response response = connection.request(new TransactionInfo(connectionInfo.getConnectionId(), null, TransactionInfo.RECOVER));
        assertNotNull(response);
        DataArrayResponse dar = (DataArrayResponse)response;

        //These should be purged so expect 0
        assertEquals(0, dar.getData().length);

    }

};