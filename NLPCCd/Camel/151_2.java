//,temp,sample_1213.java,2,12,temp,sample_7673.java,2,12
//,3
public class xxx {
public void testIrcPrivateMessages() throws Exception {
resultEndpoint = context.getEndpoint("mock:result", MockEndpoint.class);
resultEndpoint.expectedBodiesReceived(expectedBody1, expectedBody2);
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
for (Exchange exchange : list) {


log.info("received exchange headers");
}
}

};