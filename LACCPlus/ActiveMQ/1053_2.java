//,temp,XARecoveryBrokerTest.java,1144,1243,temp,XARecoveryBrokerTest.java,1041,1141
//,3
public class xxx {
    public void testQueuePersistentPreparedAcksAvailableAfterRollbackPrefetchOne() throws Exception {

        ActiveMQDestination destination = createDestination();

        // Setup the producer and send the message.
        StubConnection connection = createConnection();
        ConnectionInfo connectionInfo = createConnectionInfo();
        SessionInfo sessionInfo = createSessionInfo(connectionInfo);
        ProducerInfo producerInfo = createProducerInfo(sessionInfo);
        connection.send(connectionInfo);
        connection.send(sessionInfo);
        connection.send(producerInfo);

        int numMessages = 1;
        for (int i = 0; i < numMessages; i++) {
            Message message = createMessage(producerInfo, destination);
            message.setPersistent(true);
            connection.send(message);
        }

        // Begin the transaction.
        XATransactionId txid = createXATransaction(sessionInfo);
        connection.send(createBeginTransaction(connectionInfo, txid));

        // use consumer per destination for the composite dest case
        // bc the same composite dest is used for sending so there
        // will be duplicate message ids in the mix which a single
        // consumer (PrefetchSubscription) cannot handle in a tx
        // atm. The matching is based on messageId rather than messageId
        // and destination
        Set<ConsumerInfo> consumerInfos = new HashSet<ConsumerInfo>();
        for (ActiveMQDestination dest : destinationList(destination)) {
            ConsumerInfo consumerInfo = createConsumerInfo(sessionInfo, dest);
            consumerInfo.setPrefetchSize(numMessages);
            consumerInfos.add(consumerInfo);
        }

        for (ConsumerInfo info : consumerInfos) {
            connection.send(info);
        }

        Message message = null;
        for (ConsumerInfo info : consumerInfos) {
            for (int i = 0; i < numMessages; i++) {
               message = receiveMessage(connection);
               assertNotNull(message);
               connection.send(createAck(info, message, 1, MessageAck.DELIVERED_ACK_TYPE));
            }
            MessageAck ack = createAck(info, message, numMessages, MessageAck.STANDARD_ACK_TYPE);
            ack.setTransactionId(txid);
            connection.send(ack);
        }
        connection.request(createPrepareTransaction(connectionInfo, txid));

        // reconnect
        connection.send(connectionInfo.createRemoveCommand());
        connection = createConnection();
        connection.send(connectionInfo);

        // validate recovery
        TransactionInfo recoverInfo = new TransactionInfo(connectionInfo.getConnectionId(), null, TransactionInfo.RECOVER);
        DataArrayResponse dataArrayResponse = (DataArrayResponse) connection.request(recoverInfo);

        assertEquals("there is a prepared tx", 1, dataArrayResponse.getData().length);
        assertEquals("it matches", txid, dataArrayResponse.getData()[0]);

        connection.send(sessionInfo);

        for (ConsumerInfo info : consumerInfos) {
            connection.send(info);
        }

        // no redelivery, exactly once semantics while prepared
        message = receiveMessage(connection);
        assertNull(message);
        assertNoMessagesLeft(connection);

        // rollback so we get redelivery
        connection.request(createRollbackTransaction(connectionInfo, txid));

        LOG.info("new tx for redelivery");
        txid = createXATransaction(sessionInfo);
        connection.send(createBeginTransaction(connectionInfo, txid));

        for (ConsumerInfo info : consumerInfos) {
            for (int i = 0; i < numMessages; i++) {
                message = receiveMessage(connection);
                assertNotNull("unexpected null on:" + i, message);
                MessageAck ack = createAck(info, message, 1, MessageAck.STANDARD_ACK_TYPE);
                ack.setTransactionId(txid);
                connection.send(ack);
            }
        }

        // Commit
        connection.request(createCommitTransaction1Phase(connectionInfo, txid));

        // validate recovery complete
        dataArrayResponse = (DataArrayResponse) connection.request(recoverInfo);
        assertEquals("there are no prepared tx", 0, dataArrayResponse.getData().length);
    }

};