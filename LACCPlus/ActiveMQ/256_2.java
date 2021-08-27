//,temp,Stomp11Test.java,445,467,temp,StompInactivityMonitorTest.java,60,92
//,3
public class xxx {
    @Test
    public void test() throws Exception {
        stompConnect();

        String connectFrame = "STOMP\n" +
            "login:system\n" +
            "passcode:manager\n" +
            "accept-version:1.1\n" +
            "heart-beat:1000,0\n" +
            "host:localhost\n" +
            "\n" + Stomp.NULL;

        stompConnection.sendFrame(connectFrame);
        String response = stompConnection.receiveFrame().trim();
        LOG.info("Broker sent response: {}", response);

        String messageHead = "SEND\n" +
                             "receipt:1" + "\n" +
                             "destination:/queue/" + getQueueName() +
                             "\n\n" + "AAAA";

        stompConnection.sendFrame(messageHead);

        for (int i = 0; i < 30; i++) {
            stompConnection.sendFrame("A");
            Thread.sleep(100);
        }

        stompConnection.sendFrame(Stomp.NULL);

        response = stompConnection.receiveFrame().trim();
        assertTrue(response.startsWith("RECEIPT"));
    }

};