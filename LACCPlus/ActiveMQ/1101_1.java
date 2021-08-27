//,temp,Stomp11Test.java,1018,1131,temp,Stomp11Test.java,854,943
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testMultipleDurableSubsWithOfflineMessages() throws Exception {
        stompConnection.setVersion(Stomp.V1_1);

        final BrokerViewMBean view = getProxyToBroker();

        String connectFrame = "STOMP\n" + "login:system\n" + "passcode:manager\n" +
                "accept-version:1.1\n" + "host:localhost\n" + "client-id:test\n" + "\n" + Stomp.NULL;
        stompConnection.sendFrame(connectFrame);

        String frame = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + frame);

        assertTrue(frame.startsWith("CONNECTED"));
        assertEquals(view.getDurableTopicSubscribers().length, 0);

        // subscribe to first destination durably
        frame = "SUBSCRIBE\n" +
                "destination:/topic/" + getQueueName() + "1" + "\n" +
                "ack:auto\n" + "receipt:1\n" + "id:durablesub-1\n" +
                "activemq.subscriptionName:test1\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("1", receipt.getHeaders().get("receipt-id"));
        assertEquals(view.getDurableTopicSubscribers().length, 1);

        // subscribe to second destination durably
        frame = "SUBSCRIBE\n" +
                "destination:/topic/" + getQueueName() + "2" + "\n" +
                "ack:auto\n" + "receipt:2\n" + "id:durablesub-2\n" +
                "activemq.subscriptionName:test2\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("2", receipt.getHeaders().get("receipt-id"));
        assertEquals(view.getDurableTopicSubscribers().length, 2);

        frame = "DISCONNECT\nclient-id:test\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        assertTrue(Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return view.getCurrentConnectionsCount() == 1;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25)));

        // reconnect and send some messages to the offline subscribers and then try to get
        // them after subscribing again.
        stompConnect();
        stompConnection.sendFrame(connectFrame);
        frame = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + frame);
        assertTrue(frame.contains("CONNECTED"));
        assertEquals(view.getDurableTopicSubscribers().length, 0);
        assertEquals(view.getInactiveDurableTopicSubscribers().length, 2);

        frame = "SEND\n" + "destination:/topic/" + getQueueName() + "1\n" +
                "receipt:10\n" + "\n" + "Hello World 1" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        receipt = stompConnection.receive();
        assertEquals("10", receipt.getHeaders().get(Stomp.Headers.Response.RECEIPT_ID));

        frame = "SEND\n" + "destination:/topic/" + getQueueName() + "2\n" +
                "receipt:11\n" + "\n" + "Hello World 2" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        receipt = stompConnection.receive();
        assertEquals("11", receipt.getHeaders().get(Stomp.Headers.Response.RECEIPT_ID));

        // subscribe to first destination durably
        frame = "SUBSCRIBE\n" +
                "destination:/topic/" + getQueueName() + "1" + "\n" +
                "ack:auto\n" + "receipt:3\n" + "id:durablesub-1\n" +
                "activemq.subscriptionName:test1\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("3", receipt.getHeaders().get("receipt-id"));
        assertEquals(view.getDurableTopicSubscribers().length, 1);

        StompFrame message = stompConnection.receive();
        assertEquals(Stomp.Responses.MESSAGE, message.getAction());
        assertEquals("durablesub-1", message.getHeaders().get(Stomp.Headers.Message.SUBSCRIPTION));

        assertEquals(view.getDurableTopicSubscribers().length, 1);
        assertEquals(view.getInactiveDurableTopicSubscribers().length, 1);

        // subscribe to second destination durably
        frame = "SUBSCRIBE\n" +
                "destination:/topic/" + getQueueName() + "2" + "\n" +
                "ack:auto\n" + "receipt:4\n" + "id:durablesub-2\n" +
                "activemq.subscriptionName:test2\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("4", receipt.getHeaders().get("receipt-id"));
        assertEquals(view.getDurableTopicSubscribers().length, 2);

        message = stompConnection.receive();
        assertEquals(Stomp.Responses.MESSAGE, message.getAction());
        assertEquals("durablesub-2", message.getHeaders().get(Stomp.Headers.Message.SUBSCRIPTION));

        assertEquals(view.getDurableTopicSubscribers().length, 2);
        assertEquals(view.getInactiveDurableTopicSubscribers().length, 0);
    }

};