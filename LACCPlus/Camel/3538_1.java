//,temp,DirectMessageProducerIT.java,48,67,temp,UserProducerInOutIT.java,51,73
//,3
public class xxx {
    @Test
    public void testDirectMessage() throws Exception {
        Date now = new Date();
        String tweet = "Test a tweet posted on " + now.toString();
        LOG.info("Tweet: " + tweet);
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        // send tweet to the twitter endpoint
        producerTemplate.sendBody("direct:tweets", tweet);

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.expectedBodyReceived().body(String.class);
        // Message headers should be preserved
        resultEndpoint.assertIsSatisfied();

        List<Exchange> tweets = resultEndpoint.getExchanges();
        assertNotNull(tweets);
        assertThat(tweets.size(), is(1));
        String receivedTweet = tweets.get(0).getIn().getBody(String.class);
        assertThat(receivedTweet, is(tweet));
    }

};