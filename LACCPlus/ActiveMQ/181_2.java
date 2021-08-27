//,temp,StompEmptyDestinationTest.java,27,46,temp,Stomp12Test.java,72,106
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testTelnetStyleSends() throws Exception {
        stompConnection.setVersion(Stomp.V1_2);

        String connect = "CONNECT\r\n" +
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

        String send = "SUBSCRIBE\r\n" +
                      "id:1\r\n" +
                      "destination:/queue/" + getQueueName() + "\r\n" +
                      "receipt:1\r\n" +
                      "\r\n"+
                      "\u0000\r\n";

        stompConnection.sendFrame(send);

        StompFrame receipt = stompConnection.receive();
        LOG.info("Broker sent: " + receipt);
        assertTrue(receipt.getAction().startsWith("RECEIPT"));
        String receiptId = receipt.getHeaders().get("receipt-id");
        assertEquals("1", receiptId);
    }

};