//,temp,TwoBrokerQueueClientsReconnectTest.java,317,368,temp,TwoBrokerQueueClientsReconnectTest.java,187,227
//,3
public class xxx {
    public void testTwoClientsReceiveTwoClientReconnects() throws Exception {
        // ensure all message do not flow across the network too quickly
        applyRateLimitNetworkFilter(0.5 * MESSAGE_COUNT);

        broker1 = "BrokerA";
        broker2 = "BrokerB";

        // Bridge brokers
        bridgeBrokers(broker1, broker2);
        bridgeBrokers(broker2, broker1);

        // Run brokers
        startAllBrokers();

        // Create queue
        Destination dest = createDestination("TEST.FOO", false);

        // Create the first client
        MessageConsumer client1 = createConsumer(broker1, dest);
        MessageConsumer client2 = createConsumer(broker2, dest);

        // Give clients time to register with broker
        Thread.sleep(500);

        // Always send messages to broker A
        sendMessages("BrokerA", dest, MESSAGE_COUNT);

        // Let each client receive 20% of the messages - 40% total
        msgsClient1 += receiveExactMessages(client1, (int)(MESSAGE_COUNT * 0.20));
        msgsClient2 += receiveExactMessages(client2, (int)(MESSAGE_COUNT * 0.20));

        LOG.info("Disconnect both clients");
        client1.close();
        client2.close();

        // Let each client receive 30% more of the total messages - 60% total
        LOG.info("Serially create another two clients for each broker and consume in turn");
        client1 = createConsumer(broker1, dest);
        msgsClient1 += receiveExactMessages(client1, (int)(MESSAGE_COUNT * 0.30));
        client1.close();

        // the close will allow replay or the replay of the remaining messages
        client2 = createConsumer(broker2, dest);
        msgsClient2 += receiveExactMessages(client2, (int)(MESSAGE_COUNT * 0.30));
        client2.close();

        // First client should have received 50% of the messages
        assertEquals("Client for " + broker1 + " should have received 50% of the messages.", (int)(MESSAGE_COUNT * 0.50), msgsClient1);

        // Second client should have received 50% of the messages
        assertEquals("Client for " + broker2 + " should have received 50% of the messages.", (int)(MESSAGE_COUNT * 0.50), msgsClient2);
    }

};