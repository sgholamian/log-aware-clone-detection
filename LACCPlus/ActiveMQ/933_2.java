//,temp,TwoBrokerQueueClientsReconnectTest.java,317,368,temp,TwoBrokerQueueClientsReconnectTest.java,187,227
//,3
public class xxx {
    public void doTwoClientsReceiveOneClientDisconnects() throws Exception {
        // ensure all message do not flow across the network too quickly
        applyRateLimitNetworkFilter(0.8 * MESSAGE_COUNT);

        // Bridge brokers
        bridgeBrokers(broker1, broker2);
        bridgeBrokers(broker2, broker1);

        // Run brokers
        startAllBrokers();

        // Create queue
        Destination dest = createDestination("TEST.FOO", false);

        // Create first client
        MessageConsumer client1 = createConsumer(broker1, dest);
        MessageConsumer client2 = createConsumer(broker2, dest);

        // Give clients time to register with broker
        Thread.sleep(500);

        // Always send messages to broker A
        sendMessages("BrokerA", dest, MESSAGE_COUNT);

        LOG.info("Let each client receive 20% of the messages - 40% total");
        msgsClient1 += receiveExactMessages(client1, (int)(MESSAGE_COUNT * 0.20));
        msgsClient2 += receiveExactMessages(client2, (int)(MESSAGE_COUNT * 0.20));

        // Disconnect the first client
        client1.close();

        LOG.info("Let the second client receive the rest of the messages");
        msgsClient2 += receiveAllMessages(client2);
        client2.close();

        // First client should have received 20% of the messages
        assertEquals("Client for " + broker1 + " should have received 20% of the messages.", (int)(MESSAGE_COUNT * 0.20), msgsClient1);

        // Second client should have received 80% of the messages
        assertEquals("Client for " + broker2 + " should have received 80% of the messages.", (int)(MESSAGE_COUNT * 0.80), msgsClient2);
    }

};