//,temp,XARecoveryBrokerTest.java,1591,1677,temp,XARecoveryBrokerTest.java,1345,1418
//,3
public class xxx {
    public void testNoDupOnRollbackRedelivery() throws Exception {

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

        int numMessages = 1;
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
        connection.request(createRollbackTransaction(connectionInfo, txid));

        connection.send(consumerInfo.createRemoveCommand());
        connection.send(sessionInfo.createRemoveCommand());
        connection.send(connectionInfo.createRemoveCommand());


        LOG.info("new connection/consumer for redelivery");

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

        message = receiveMessage(connection);
        assertNotNull(message);

        Message dup = receiveMessage(connection);
        assertNull("no duplicate send: " + dup, dup);

        txid = createXATransaction(sessionInfo);
        connection.send(createBeginTransaction(connectionInfo, txid));

        ack = createAck(consumerInfo, message, numMessages, MessageAck.STANDARD_ACK_TYPE);
        ack.setTransactionId(txid);
        connection.send(ack);

        connection.request(createEndTransaction(connectionInfo, txid));
        connection.request(createCommitTransaction1Phase(connectionInfo, txid));

        // unsubscribe
        connection.request(consumerInfo.createRemoveCommand());
        RemoveSubscriptionInfo removeSubscriptionInfo = new RemoveSubscriptionInfo();
        removeSubscriptionInfo.setClientId(connectionInfo.getClientId());
        removeSubscriptionInfo.setSubscriptionName(consumerInfo.getSubscriptionName());
        connection.request(removeSubscriptionInfo);
    }

};