//,temp,Stomp11Test.java,945,1016,temp,Stomp12Test.java,669,731
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testDurableSubAndUnSubFlow() throws Exception {
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

        // attempt to remove the durable subscription while there is an active subscription
        frame = "UNSUBSCRIBE\n" + "destination:/topic/" + getQueueName() + "1\n" +
                "id:durablesub-1\n" + "receipt:3\n" +
                "activemq.subscriptionName:test1\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("ERROR"));
        assertEquals("3", receipt.getHeaders().get("receipt-id"));

        assertEquals(view.getInactiveDurableTopicSubscribers().length, 0);
        assertEquals(view.getDurableTopicSubscribers().length, 1);

        // attempt to remove the subscriber leaving the durable sub in place.
        frame = "UNSUBSCRIBE\n" + "destination:/topic/" + getQueueName() + "1\n" +
                "id:durablesub-1\n" + "receipt:4\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("4", receipt.getHeaders().get("receipt-id"));

        assertEquals(view.getInactiveDurableTopicSubscribers().length, 1);
        assertEquals(view.getDurableTopicSubscribers().length, 0);

        // attempt to remove the durable subscription which should succeed since there are no
        // active durable subscribers
        frame = "UNSUBSCRIBE\n" + "destination:/topic/" + getQueueName() + "1\n" +
                "id:durablesub-1\n" + "receipt:5\n" +
                "activemq.subscriptionName:test1\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        receipt = stompConnection.receive();
        LOG.debug("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        assertEquals("5", receipt.getHeaders().get("receipt-id"));

        assertEquals(view.getInactiveDurableTopicSubscribers().length, 0);
        assertEquals(view.getDurableTopicSubscribers().length, 0);
    }

};