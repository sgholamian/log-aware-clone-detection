//,temp,sample_1536.java,2,14,temp,sample_1496.java,2,12
//,3
public class xxx {
public void testVertxMessages() throws Exception {
resultEndpoint = context.getEndpoint(resultUri, MockEndpoint.class);
resultEndpoint.expectedBodiesReceivedInAnyOrder(body1, body2);
template.sendBody(startUri, body1);
template.sendBody(startUri, body2);
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
for (Exchange exchange : list) {


log.info("received exchange headers");
}
}

};