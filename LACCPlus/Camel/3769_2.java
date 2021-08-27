//,temp,SearchByExchangeDirectIT.java,62,73,temp,SearchByExchangeDirectIT.java,49,60
//,3
public class xxx {
    @Test
    public void testSearchTimelineWithStaticQuery() throws Exception {
        template.sendBody(null);

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);
        mock.assertIsSatisfied();
        List<Exchange> tweets = mock.getExchanges();
        for (Exchange e : tweets) {
            log.info("Tweet: " + e.getIn().getBody(String.class));
        }
    }

};