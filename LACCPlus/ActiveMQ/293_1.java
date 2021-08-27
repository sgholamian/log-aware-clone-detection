//,temp,AjaxTest.java,371,418,temp,AjaxTest.java,105,150
//,3
public class xxx {
    @Test(timeout = 15 * 1000)
    public void testAjaxClientReceivesMessagesForMultipleTopics() throws Exception {
        LOG.debug( "*** testAjaxClientReceivesMessagesForMultipleTopics ***" );
        int port = getPort();

        HttpClient httpClient = new HttpClient();
        httpClient.start();

        LOG.debug( "SENDING LISTEN FOR /topic/topicA" );
        String sessionId = subscribe(httpClient, port, "destination=topic://topicA&type=listen&message=handlerA");


        LOG.debug( "SENDING LISTEN FOR /topic/topicB" );
        subscribe(httpClient, port, "destination=topic://topicB&type=listen&message=handlerB", null, sessionId);

        // client 1 polls for messages
        final StringBuffer buf = new StringBuffer();
        final CountDownLatch latch =
                asyncRequest(httpClient, "http://localhost:" + port + "/amq?timeout=5000", buf, sessionId);

        // while client 1 is polling, client 2 sends messages to the topics
        LOG.debug( "SENDING MESSAGES" );
        sendMessages(httpClient, port, ("destination=topic://topicA&type=send&message=A1&"+
                "d1=topic://topicB&t1=send&m1=B1&"+
                "d2=topic://topicA&t2=send&m2=A2&"+
                "d3=topic://topicB&t3=send&m3=B2").getBytes());
        LOG.debug( "DONE POSTING MESSAGES" );

        // wait for poll to finish
        latch.await();
        String response = buf.toString();

        // not all messages might be delivered during the 1st poll.  We need to check again.
        final StringBuffer buf2 = new StringBuffer();
        final CountDownLatch latch2 =
                asyncRequest(httpClient, "http://localhost:" + port + "/amq?timeout=5000", buf2, sessionId);
        latch2.await();

        String fullResponse = response + buf2.toString();
        LOG.debug( "full response " + fullResponse );
        assertContains( "<response id='handlerA' destination='topic://topicA' >A1</response>", fullResponse );
        assertContains( "<response id='handlerB' destination='topic://topicB' >B1</response>", fullResponse );
        assertContains( "<response id='handlerA' destination='topic://topicA' >A2</response>", fullResponse );
        assertContains( "<response id='handlerB' destination='topic://topicB' >B2</response>", fullResponse );
        assertResponseCount( 4, fullResponse );

        httpClient.stop();
     }

};