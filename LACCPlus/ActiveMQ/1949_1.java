//,temp,RestTest.java,62,79,temp,RestTest.java,44,60
//,2
public class xxx {
    @Test(timeout = 60 * 1000)
    public void testSubscribeFirst() throws Exception {
        int port = getPort();

        HttpClient httpClient = new HttpClient();
        httpClient.start();

        final StringBuffer buf = new StringBuffer();
        final CountDownLatch latch =
                asyncRequest(httpClient, "http://localhost:" + port + "/message/test?readTimeout=5000&type=queue", buf);

        producer.send(session.createTextMessage("test"));
        LOG.info("message sent");

        latch.await();
        assertEquals("test", buf.toString());

    }

};