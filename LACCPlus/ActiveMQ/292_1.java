//,temp,AjaxTest.java,152,185,temp,AjaxTest.java,59,103
//,3
public class xxx {
    @Test(timeout = 15 * 1000)
    public void testAjaxClientReceivesMessagesWhichAreQueuedBeforeClientSubscribes() throws Exception {
        LOG.debug( "*** testAjaxClientReceivesMessagesWhichAreQueuedBeforeClientSubscribes ***" );
        int port = getPort();

        // send messages to queue://test
        producer.send( session.createTextMessage("test one") );
        producer.send( session.createTextMessage("test two") );
        producer.send( session.createTextMessage("test three") );

        HttpClient httpClient = new HttpClient();
        httpClient.start();

        // client 1 subscribes to queue
        LOG.debug( "SENDING LISTEN" );
        String sessionId = subscribe(httpClient, port, "destination=queue://test&type=listen&message=handler");

        // client 1 polls for messages
        LOG.debug( "SENDING POLL" );
        final StringBuffer buf = new StringBuffer();
        final CountDownLatch latch =
                asyncRequest(httpClient, "http://localhost:" + port + "/amq?timeout=5000", buf, sessionId);

        // wait for poll to finish
        latch.await();
        String response = buf.toString();

        assertContains( "<response id='handler' destination='queue://test' >test one</response>", response );
        assertContains( "<response id='handler' destination='queue://test' >test two</response>", response );
        assertContains( "<response id='handler' destination='queue://test' >test three</response>", response );
        assertResponseCount( 3, response );

        httpClient.stop();
    }

};