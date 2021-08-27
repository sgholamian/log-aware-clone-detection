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

        wsStompConnection.sendRawFrame(connectFrame);
        String incoming = wsStompConnection.receive(30, TimeUnit.SECONDS);
        assertTrue(incoming.startsWith("CONNECTED"));
        assertTrue(incoming.indexOf("version:1.1") >= 0);
        assertTrue(incoming.indexOf("heart-beat:") >= 0);
        assertTrue(incoming.indexOf("session:") >= 0);

        String message = "SEND\n" + "destination:/queue/" + getTestName() + "\n\n" + "Hello World" + Stomp.NULL;
        wsStompConnection.sendRawFrame(message);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    LOG.info("Sending next KeepAlive");
                    wsStompConnection.keepAlive();
                } catch (Exception e) {
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(15);

        String frame = "SUBSCRIBE\n" + "destination:/queue/" + getTestName() + "\n" +
                       "id:12345\n" + "ack:auto\n\n" + Stomp.NULL;
        wsStompConnection.sendRawFrame(frame);

        incoming = wsStompConnection.receive(30, TimeUnit.SECONDS);
        assertTrue(incoming.startsWith("MESSAGE"));

        service.shutdownNow();
        service.awaitTermination(5, TimeUnit.SECONDS);

        try {
            wsStompConnection.sendFrame(new StompFrame(Stomp.Commands.DISCONNECT));
        } catch (Exception ex) {
            LOG.info("Caught exception on write of disconnect", ex);
        }
    }

};