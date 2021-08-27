//,temp,Stomp11Test.java,535,575,temp,Stomp11Test.java,499,533
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testAckMessageWithNoId() throws Exception {

        String connectFrame = "STOMP\n" +
                              "login:system\n" +
                              "passcode:manager\n" +
                              "accept-version:1.1\n" +
                              "host:localhost\n" +
                              "\n" + Stomp.NULL;
        stompConnection.sendFrame(connectFrame);

        String f = stompConnection.receiveFrame();
        LOG.debug("Broker sent: " + f);

        assertTrue(f.startsWith("CONNECTED"));

        String message = "SEND\n" + "destination:/queue/" + getQueueName() +
                         "\n\n" + "Hello World" + Stomp.NULL;
        stompConnection.sendFrame(message);

        String subscribe = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                           "activemq.prefetchSize=1" + "\n" +
                           "id:12345\n" + "ack:client\n\n" + Stomp.NULL;
        stompConnection.sendFrame(subscribe);

        StompFrame received = stompConnection.receive();
        LOG.info("Received Frame: {}", received);
        assertTrue("Expected MESSAGE but got: " + received.getAction(), received.getAction().equals("MESSAGE"));

        String ack = "ACK\n" + "message-id:" +
                     received.getHeaders().get("message-id") + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(ack);

        StompFrame error = stompConnection.receive();
        LOG.info("Received Frame: {}", error);
        assertTrue("Expected ERROR but got: " + error.getAction(), error.getAction().equals("ERROR"));

        String unsub = "UNSUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                       "id:12345\n\n" + Stomp.NULL;
        stompConnection.sendFrame(unsub);
    }

};