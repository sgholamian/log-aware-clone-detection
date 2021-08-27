//,temp,FanoutTransportBrokerTest.java,63,102,temp,DiscoveryTransportBrokerTest.java,53,117
//,3
public class xxx {
    public void testPublisherFansout() throws Exception {

        // Start a normal consumer on the local broker
        StubConnection connection1 = createConnection();
        ConnectionInfo connectionInfo1 = createConnectionInfo();
        SessionInfo sessionInfo1 = createSessionInfo(connectionInfo1);
        ConsumerInfo consumerInfo1 = createConsumerInfo(sessionInfo1, destination);
        connection1.send(connectionInfo1);
        connection1.send(sessionInfo1);
        connection1.request(consumerInfo1);

        // Start a normal consumer on a remote broker
        StubConnection connection2 = createRemoteConnection();
        ConnectionInfo connectionInfo2 = createConnectionInfo();
        SessionInfo sessionInfo2 = createSessionInfo(connectionInfo2);
        ConsumerInfo consumerInfo2 = createConsumerInfo(sessionInfo2, destination);
        connection2.send(connectionInfo2);
        connection2.send(sessionInfo2);
        connection2.request(consumerInfo2);

        // Start a fanout publisher.
        LOG.info("Starting the fanout connection.");
        StubConnection connection3 = createFanoutConnection();
        ConnectionInfo connectionInfo3 = createConnectionInfo();
        SessionInfo sessionInfo3 = createSessionInfo(connectionInfo3);
        ProducerInfo producerInfo3 = createProducerInfo(sessionInfo3);
        connection3.send(connectionInfo3);
        connection3.send(sessionInfo3);
        connection3.send(producerInfo3);

        // Send the message using the fail over publisher.
        connection3.request(createMessage(producerInfo3, destination, deliveryMode));

        assertNotNull(receiveMessage(connection1));
        assertNoMessagesLeft(connection1);

        assertNotNull(receiveMessage(connection2));
        assertNoMessagesLeft(connection2);

    }

};