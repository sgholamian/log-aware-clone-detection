//,temp,Stomp11Test.java,814,852,temp,Stomp11Test.java,761,812
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testNackMessage() throws Exception {

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

        String message = "SEND\n" + "destination:/queue/" + getQueueName() + "\npersistent:true\n\n" + "Hello World" + Stomp.NULL;

        stompConnection.sendFrame(message);

        String frame = "SUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                "id:12345\n" + "ack:client\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        StompFrame received = stompConnection.receive();
        assertTrue(received.getAction().equals("MESSAGE"));

        // nack it
        frame = "NACK\n" + "subscription:12345\n" + "message-id:" +
                received.getHeaders().get("message-id") + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = "UNSUBSCRIBE\n" + "destination:/queue/" + getQueueName() + "\n" +
                "id:12345\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        //consume it from dlq

        frame = "SUBSCRIBE\n" + "destination:/queue/ActiveMQ.DLQ\n" +
                "id:12345\n" + "ack:client\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
        StompFrame receivedDLQ = stompConnection.receive(200);
        assertEquals(receivedDLQ.getHeaders().get("message-id"), received.getHeaders().get("message-id"));

        frame = "ACK\n" + "subscription:12345\n" + "message-id:" +
                received.getHeaders().get("message-id") + "\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);

        frame = "UNSUBSCRIBE\n" + "destination:/queue/ActiveMQ.DLQ\n" +
                "id:12345\n\n" + Stomp.NULL;
        stompConnection.sendFrame(frame);
    }

};