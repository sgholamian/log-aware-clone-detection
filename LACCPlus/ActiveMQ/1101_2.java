//,temp,Stomp11Test.java,1018,1131,temp,Stomp11Test.java,854,943
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testDurableSubAndUnSubOnTwoTopics() throws Exception {
        stompConnection.setVersion(Stomp.V1_1);

        String domain = "org.apache.activemq";
        ObjectName brokerName = new ObjectName(domain + ":type=Broker,brokerName=localhost");

        BrokerViewMBean view = (BrokerViewMBean)brokerService.getManagementContext().newProxyInstance(brokerName, BrokerViewMBean.class, true);

        String connectFrame = "STOMP\n" +
                "login:system\n" + "passcode:manager\n" + "accept-version:1.1\n" +
                "host:localhost\n" + "client-id:test\n" + "\n" + Stomp.NULL;
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
        Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToBroker().getCurrentConnectionsCount() == 0;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25));

        // reconnect and send some messages to the offline subscribers and then try to get
        // them after subscribing again.
        stompConnect();
        stompConnection.sendFrame(connectFrame);
        frame = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + frame);
        assertTrue(frame.startsWith("CONNECTED"));
        assertEquals(view.getDurableTopicSubscribers().length, 0);
        assertEquals(view.getInactiveDurableTopicSubscribers().length, 2);

        // unsubscribe from topic 1
        frame = "UNSUBSCRIBE\n" + "destination:/topic/" + getQueueName() + "1\n" +
                "id:durablesub-1\n" + "receipt:3\n" +
                "activemq.subscriptionName:test1\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + frame);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("3", receipt.getHeaders().get("receipt-id"));

        assertEquals(view.getInactiveDurableTopicSubscribers().length, 1);

        // unsubscribe from topic 2
        frame = "UNSUBSCRIBE\n" + "destination:/topic/" + getQueueName() + "2\n" +
                "id:durablesub-2\n" + "receipt:4\n" +
                "activemq.subscriptionName:test2\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + frame);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("4", receipt.getHeaders().get("receipt-id"));

        assertEquals(view.getInactiveDurableTopicSubscribers().length, 0);
    }

};