//,temp,Stomp12Test.java,941,1057,temp,Stomp12Test.java,377,494
//,3
public class xxx {
    private void doTestMessagesRetirementAfterTransactionAbortClientAckMode(boolean nack) throws Exception {
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

        // Subscribe to the queue
        String subscribe = "SUBSCRIBE\n" +
                           "id:1\n" +
                           "activemq.prefetchSize:" + MESSAGE_COUNT + "\n" +
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

        // Start a TX that will later be aborted.
        String begin = "BEGIN\n" + "transaction:tx1" + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(begin);

        List<String> ackIds = new ArrayList<>(MESSAGE_COUNT);

        for (int n = 0; n < MESSAGE_COUNT; n++) {
            StompFrame received = stompConnection.receive();
            LOG.info("Broker sent: " + received);
            assertTrue(received.getAction().equals("MESSAGE"));
            assertTrue(received.getHeaders().containsKey(Stomp.Headers.Message.ACK_ID));
            assertEquals(String.format("%d", n), received.getBody());

            ackIds.add(received.getHeaders().get(Stomp.Headers.Message.ACK_ID));
        }

        // Client ACK that enlists all messages in the TX
        String frame = "ACK\n" + "transaction:tx1" + "\n" + "id:" + ackIds.get(MESSAGE_COUNT - 1) + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        assertTrue(Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToQueue(getQueueName()).getQueueSize() == MESSAGE_COUNT;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25)));

        String commit = "ABORT\n" + "transaction:tx1" + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(commit);

        assertTrue(Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToQueue(getQueueName()).getQueueSize() == MESSAGE_COUNT;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25)));

        for (String ackId : ackIds) {
            if (nack) {
                frame = "NACK\n" + "id:" + ackId + "\n" + "receipt:2" + "\n\n" + Stomp.NULL;
                stompConnection.sendFrame(frame);
            } else {
                frame = "ACK\n" + "id:" + ackId + "\n" + "receipt:2" + "\n\n" + Stomp.NULL;
                stompConnection.sendFrame(frame);
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

        frame = "DISCONNECT\n" + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        Wait.waitFor(new Wait.Condition() {

            @Override
            public boolean isSatisified() throws Exception {
                return getProxyToBroker().getCurrentConnectionsCount() <= 1;
            }
        }, TimeUnit.SECONDS.toMillis(5), TimeUnit.MILLISECONDS.toMillis(25));
    }

};