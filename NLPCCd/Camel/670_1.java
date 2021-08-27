//,temp,sample_416.java,2,13,temp,sample_417.java,2,13
//,3
public class xxx {
public void testSearchTimelineWithStaticQuery() throws Exception {
template.sendBody(null);
MockEndpoint mock = getMockEndpoint("mock:result");
mock.expectedMinimumMessageCount(1);
mock.assertIsSatisfied();
List<Exchange> tweets = mock.getExchanges();
for (Exchange e : tweets) {


log.info("tweet");
}
}

};