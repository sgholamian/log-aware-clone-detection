//,temp,Stomp12Test.java,292,375,temp,Stomp12Test.java,108,182
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testClientAckMultipleMessagesWithSingleAck() throws Exception {
        final int MESSAGE_COUNT = 10;

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

        // Send some messages
        for (int n = 0; n < MESSAGE_COUNT; n++) {
            String message = "SEND\n" + "destination:/queue/" + getQueueName() + "\n\n" + String.format("%d", n) + Stomp.NULL;
            stompConnection.sendFrame(message);
        }

        String subscribe = "SUBSCRIBE\n" +
                           "id:1\n" +
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
        assertEquals(MESSAGE_COUNT, getProxyToQueue(getQueueName()).getQueueSize());

        String lastAckId = null;

        for (int n = 0; n < MESSAGE_COUNT; n++) {
            StompFrame received = stompConnection.receive();
            LOG.info("Broker sent: " + received);
            assertTrue(received.getAction().equals("MESSAGE"));
            assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
            assertEquals(String.format("%d", n), received.getBody());

            lastAckId = received.getHeaders().get(Stomp.Headers.Message.ACK_ID);
        }

        String frame = "ACK\n" + "id:" +  lastAckId + "\n" + "receipt:2" + "\n\n" + Stomp.NULL;
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

        assertTrue(Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToBroker().getCurrentConnectionsCount() == 1;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25)));
    }

};