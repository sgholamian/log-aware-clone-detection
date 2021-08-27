//,temp,AjaxTest.java,152,185,temp,AjaxTest.java,59,103
//,3
public class xxx {
    @Test(timeout = 15 * 1000)
    public void testAjaxClientReceivesMessagesWhichAreSentToQueueWhileClientIsPolling() throws Exception {
        LOG.debug( "*** testAjaxClientReceivesMessagesWhichAreSentToQueueWhileClientIsPolling ***" );
        int port = getPort();

        HttpClient httpClient = new HttpClient();
        httpClient.start();

        // client 1 subscribes to a queue
        LOG.debug( "SENDING LISTEN" );
        String sessionId = subscribe(httpClient, port, "destination=queue://test&type=listen&message=handler");

        // client 1 polls for messages
        LOG.debug( "SENDING POLL" );
        final StringBuffer buf = new StringBuffer();
        final CountDownLatch latch =
                asyncRequest(httpClient, "http://localhost:" + port + "/amq?timeout=5000", buf, sessionId);

        // while client 1 is polling, client 2 sends messages to the queue
        LOG.debug( "SENDING MESSAGES" );
        sendMessages(httpClient, port, ("destination=queue://test&type=send&message=msg1&"+
                         "d1=queue://test&t1=send&m1=msg2&"+
                         "d2=queue://test&t2=send&m2=msg3").getBytes());

        LOG.debug( "DONE POSTING MESSAGES" );

        // wait for poll to finish
       latch.await();
       String response = buf.toString();

        // messages might not all be delivered during the 1st poll.  We need to check again.
       final StringBuffer buf2 = new StringBuffer();
       final CountDownLatch latch2 =
               asyncRequest(httpClient, "http://localhost:" + port + "/amq?timeout=5000", buf2, sessionId);
       latch2.await();

        String fullResponse = response + buf2.toString();
        LOG.debug( "full response : " + fullResponse );
        assertContains( "<response id='handler' destination='queue://test' >msg1</response>", fullResponse );
        assertContains( "<response id='handler' destination='queue://test' >msg2</response>", fullResponse );
        assertContains( "<response id='handler' destination='queue://test' >msg3</response>", fullResponse );
        assertResponseCount( 3, fullResponse );

        httpClient.stop();
}

};