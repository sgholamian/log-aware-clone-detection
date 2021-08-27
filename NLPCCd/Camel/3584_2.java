//,temp,sample_6548.java,2,11,temp,sample_7256.java,2,9
//,3
public class xxx {
public void test() throws Exception {
resultEndpoint.setMinimumExpectedMessageCount(1);
resultEndpoint.assertIsSatisfied();
String body = resultEndpoint.getExchanges().get(0).getIn().getBody(String.class);


log.info("received usernames");
}

};