//,temp,Stomp12Test.java,941,1057,temp,Stomp12Test.java,377,494
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testClientIndividualAck() throws Exception {
        stompConnection.setVersion(Stomp.V1_2);

        String connect = "STOMP\r\n" +
                         "accept-version:1.2\r\n" +
                         "login:system\r\n" +
                         "passcode:manager\r\n" +
                         "\r\n" +
                         "\u0000\r\n";

        stompConnection.sendFrame(connect);

        String f = stompConnection.receiveFrame();
        LOG.info("Broker sent: " + f);

        assertTrue(f.startsWith("CONNECTED"));
        assertTrue(f.indexOf("version:1.2") >= 0);
        assertTrue(f.indexOf("session:") >= 0);

        String subscribe = "SUBSCRIBE\n" +
                           "id:1\n" +
                           "ack:client-individual\n" +
                           "destination:/queue/" + getQueueName() + "\n" +
                           "receipt:1\n" +
                           "\n" + Stomp.NULL;

        stompConnection.sendFrame(subscribe);

        StompFrame receipt = stompConnection.receive();
        LOG.info("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        String receiptId = receipt.getHeaders().get("receipt-id");
        assertEquals("1", receiptId);

        String message = "SEND\n" + "destination:/queue/" + getQueueName() + "\n\n" + "1" + Stomp.NULL;
        stompConnection.sendFrame(message);
        message = "SEND\n" + "destination:/queue/" + getQueueName() + "\n\n" + "2" + Stomp.NULL;
        stompConnection.sendFrame(message);

        StompFrame received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));
        assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
        assertEquals("1", received.getBody());

        received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));
        assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
        assertEquals("2", received.getBody());

        String frame = "ACK\n" + "id:" +
                received.getHeaders().get(Stomp.Headers.Message.ACK_ID) + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = "DISCONNECT\n" + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToBroker().getCurrentConnectionsCount() <= 1;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25));

        // reconnect and send some messages to the offline subscribers and then try to get
        // them after subscribing again.
        stompConnect();
        stompConnection.sendFrame(connect);
        frame = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + frame);
        assertTrue(frame.startsWith("CONNECTED"));

        stompConnection.sendFrame(subscribe);

        receipt = stompConnection.receive();
        LOG.info("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        receiptId = receipt.getHeaders().get("receipt-id");
        assertEquals("1", receiptId);

        message = "SEND\n" + "destination:/queue/" + getQueueName() + "\n\n" + "3" + Stomp.NULL;
        stompConnection.sendFrame(message);

        received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));
        assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
        assertEquals("1", received.getBody());
        String message1AckId = received.getHeaders().get(Stomp.Headers.Message.ACK_ID);

        frame = "ACK\n" + "id:" + message1AckId + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));
        assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
        assertEquals("3", received.getBody());
        String message3AckId = received.getHeaders().get(Stomp.Headers.Message.ACK_ID);

        frame = "ACK\n" + "id:" + message3AckId + "\n" + "receipt:2" + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        receipt = stompConnection.receive();
        LOG.info("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        receiptId = receipt.getHeaders().get("receipt-id");
        assertEquals("2", receiptId);

        assertTrue(Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToQueue(getQueueName()).getQueueSize() == 0;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25)));

        frame = "DISCONNECT\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
    }

};