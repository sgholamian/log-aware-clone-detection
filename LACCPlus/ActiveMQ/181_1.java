//,temp,StompEmptyDestinationTest.java,27,46,temp,Stomp12Test.java,72,106
//,3
public class xxx {
    @Test(timeout = 60000)
    public void testEmptyDestinationOnSubscribe() throws Exception{
        stompConnect();
        stompConnection.sendFrame("CONNECT\n" + "login:system\n" + "passcode:manager\n\n" + Stomp.NULL);
        StompFrame frame = stompConnection.receive();
        assertTrue(frame.toString().startsWith("CONNECTED"));
        String send = "SUBSCRIBE\r\n" +
                "id:1\r\n" +
                "destination:\r\n" +
                "receipt:1\r\n" +
                "\r\n"+
                "\u0000\r\n";

        stompConnection.sendFrame(send);
        StompFrame receipt = stompConnection.receive();
        LOG.info("Broker sent: " + receipt);
       assertTrue(receipt.getAction().startsWith("ERROR"));
       String errorMessage = receipt.getHeaders().get("message");
       assertEquals("Invalid empty or 'null' Destination header", errorMessage);
    }

};