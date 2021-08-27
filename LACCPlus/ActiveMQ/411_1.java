//,temp,Stomp12Test.java,1069,1171,temp,Stomp12Test.java,821,929
//,3
public class xxx {
    public void doTestMixedAckNackWithMessageAckIds(boolean individual) throws Exception {

        final int MESSAGE_COUNT = 20;

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

        // Subscribe to the queue
        String subscribe = "SUBSCRIBE\n" +
                           "id:1\n" +
                           "activemq.prefetchSize:" + MESSAGE_COUNT + "\n" +
                           "ack:" + (individual ? "client-individual" : "client") + "\n" +
                           "destination:/queue/" + getQueueName() + "\n" +
                           "receipt:1\n" +
                           "\n" + Stomp.NULL;

        stompConnection.sendFrame(subscribe);

        StompFrame receipt = stompConnection.receive();
        LOG.info("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        String receiptId = receipt.getHeaders().get("receipt-id");
        assertEquals("1", receiptId);

        List<String> ackIds = new ArrayList<>(MESSAGE_COUNT);

        for (int n = 0; n < MESSAGE_COUNT; n++) {
            StompFrame received = stompConnection.receive();
            LOG.info("Broker sent: " + received);
            assertTrue(received.getAction().equals("MESSAGE"));
            assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
            assertEquals(String.format("%d", n), received.getBody());

            ackIds.add(received.getHeaders().get(Stomp.Headers.Message.ACK_ID));
        }

        assertTrue(Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToQueue(getQueueName()).getQueueSize() == MESSAGE_COUNT;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25)));

        boolean nack = false;

        for (String ackId : ackIds) {
            if (nack) {
                String frame = "NACK\n" + "id:" + ackId + "\n" + "receipt:2" + "\n\n" + Stomp.NULL;
                stompConnection.sendFrame(frame);
                nack = !nack;
            } else {
                String frame = "ACK\n" + "id:" + ackId + "\n" + "receipt:2" + "\n\n" + Stomp.NULL;
                stompConnection.sendFrame(frame);
                nack = !nack;
            }

            receipt = stompConnection.receive();
            LOG.info("Broker sent: " + receipt);
            assertTrue(receipt.getAction().startsWith("RECEIPT"));
            receiptId = receipt.getHeaders().get("receipt-id");
            assertEquals("2", receiptId);
        }

        assertTrue(Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToQueue(getQueueName()).getQueueSize() == 0;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25)));

        String frame = "DISCONNECT\n" + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToBroker().getCurrentConnectionsCount() <= 1;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25));
    }

};