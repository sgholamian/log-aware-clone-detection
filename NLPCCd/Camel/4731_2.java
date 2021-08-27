//,temp,sample_37.java,2,16,temp,sample_418.java,2,16
//,2
public class xxx {
public void testSearchTimelineWithDynamicQuerySinceId() throws Exception {
Map<String, Object> headers = new HashMap<String, Object>();
headers.put(TwitterConstants.TWITTER_KEYWORDS, "java");
headers.put(TwitterConstants.TWITTER_SINCEID, new Long(258347905419730944L));
templateHeader.sendBodyAndHeaders(null, headers);
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.assertIsSatisfied();
List<Exchange> tweets = mock.getExchanges();
for (Exchange e : tweets) {


log.info("tweet");
}
}

};