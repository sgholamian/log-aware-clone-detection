//,temp,sample_1536.java,2,14,temp,sample_1496.java,2,12
//,3
public class xxx {
public void testIrcMessages() throws Exception {
resultEndpoint = context.getEndpoint("mock:result", MockEndpoint.class);
resultEndpoint.expectedBodiesReceived(body1, body2);
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
for (Exchange exchange : list) {


log.info("received exchange headers");
}
}

};