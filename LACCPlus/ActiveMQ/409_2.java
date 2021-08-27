//,temp,Stomp12Test.java,292,375,temp,Stomp12Test.java,108,182
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testClientAckWithoutAckId() throws Exception {
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
                           "activemq.prefetchSize=1\n" +
                           "ack:client\n" +
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

        StompFrame received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));
        assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
        assertEquals("1", received.getBody());

        String ackId = received.getHeaders().get(Stomp.Headers.Message.ACK_ID);

        // Put ACK ID in wrong header
        String frame = "ACK\n" + "message-id:" + ackId + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        received = stompConnection.receive();
        assertTrue(received.getAction().equals("ERROR"));
        LOG.info("Broker sent: " + received);

        // Now place it in the correct location and check it still works.
        frame = "ACK\n" + "id:" + ackId + "\n" + "receipt:2\n\n" + Stomp.NULL;
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