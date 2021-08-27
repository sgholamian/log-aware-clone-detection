//,temp,DirectMessageProducerIT.java,48,67,temp,UserProducerInOutIT.java,51,73
//,3
public class xxx {
    @Test
    public void testPostStatusUpdateRequestResponse() throws Exception {
        Date now = new Date();
        String tweet = "UserProducerInOutTest: This is a tweet posted on " + now.toString();
        LOG.info("Tweet: " + tweet);
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        // send tweet to the twitter endpoint
        producerTemplate.sendBodyAndHeader("direct:tweets", tweet, "customHeader", 12312);

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.expectedBodyReceived().body(Status.class);
        // Message headers should be preserved
        resultEndpoint.expectedHeaderReceived("customHeader", 12312);
        resultEndpoint.assertIsSatisfied();

        List<Exchange> tweets = resultEndpoint.getExchanges();
        assertNotNull(tweets);
        assertThat(tweets.size(), is(1));
        Status receivedTweet = tweets.get(0).getIn().getBody(Status.class);
        assertNotNull(receivedTweet);
        // The identifier for the published tweet should be there
        assertNotEquals(0, receivedTweet.getId());
    }

};