//,temp,sample_871.java,2,14,temp,sample_38.java,2,13
//,3
public class xxx {
public void testDoubleSearchKeepingOld() throws Exception {
templateDouble.sendBodyAndHeader(null, TwitterConstants.TWITTER_KEYWORDS, "java");
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.assertIsSatisfied();
List<Exchange> tweets = mock.getExchanges();
for (Exchange e : tweets) {


log.info("tweet");
}
}

};