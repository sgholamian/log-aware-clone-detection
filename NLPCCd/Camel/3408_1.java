//,temp,sample_5401.java,2,12,temp,sample_4146.java,2,12
//,3
public class xxx {
public void testIrcMessages() throws Exception {
resultEndpoint = context.getEndpoint("mock:result", MockEndpoint.class);
resultEndpoint.expectedBodiesReceived(resultEnd);
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
for (Exchange exchange : list) {


log.info("received exchange headers");
}
}

};