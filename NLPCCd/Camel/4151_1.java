//,temp,sample_5473.java,2,16,temp,sample_3818.java,2,16
//,3
public class xxx {
public void testUsingContextComponent() throws Exception {
Object accounts = applicationContext.getBean("accounts");
resultEndpoint.expectedHeaderReceived("received", "true");
resultEndpoint.expectedMessageCount(2);
template.sendBody("<purchaseOrder>one</purchaseOrder>");
template.sendBody("<purchaseOrder>two</purchaseOrder>");
resultEndpoint.assertIsSatisfied();
List<Exchange> receivedExchanges = resultEndpoint.getReceivedExchanges();
for (Exchange exchange : receivedExchanges) {
Message in = exchange.getIn();


log.info("received from headers body");
}
}

};