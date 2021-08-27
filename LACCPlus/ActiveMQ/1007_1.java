//,temp,Stomp11Test.java,257,302,temp,StompWSTransportTest.java,253,303
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testHeartbeatsKeepsConnectionOpen() throws Exception {

        String connectFrame = "STOMP\n" +
                              "login:system\n" +
                              "passcode:manager\n" +
                              "accept-version:1.1\n" +
                              "heart-beat:2000,0\n" +
                              "host:localhost\n" +
                              "\n" + Stomp.NULL;

        stompConnection.sendFrame(connectFrame);
        String f = stompConnection.receiveFrame();
        assertTrue(f.startsWith("CONNECTED"));
        assertTrue(f.indexOf("version:1.1") >= 0);
        assertTrue(f.indexOf("heart-beat:") >= 0);
        assertTrue(f.indexOf("session:") >= 0);
        LOG.debug("Broker sent: " + f);

        String message = "SEND\n" + "destination:/queue/" + getQueueName() + "\n\n" + "Hello World" + Stomp.NULL;
        stompConnection.sendFrame(message);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    LOG.info("Sending next KeepAlive");
                    stompConnection.keepAlive();
                } catch (Exception e) {
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(20);

        String frame = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                       "id:12345\n" + "ack:auto\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame stompFrame = stompConnection.receive();
        assertTrue(stompFrame.getAction().equals("MESSAGE"));

        service.shutdownNow();
    }

};