//,temp,sample_690.java,2,15,temp,sample_6534.java,2,15
//,2
public class xxx {
public void testQuartz() throws Exception {
resultEndpoint = getMockEndpoint("mock:result");
resultEndpoint.expectedMessageCount(2);
resultEndpoint.message(0).header("triggerName").isEqualTo("myTimerName");
resultEndpoint.message(0).header("triggerGroup").isEqualTo("myGroup");
resultEndpoint.assertIsSatisfied();
List<Exchange> list = resultEndpoint.getReceivedExchanges();
for (Exchange exchange : list) {
Message in = exchange.getIn();


log.info("received with headers");
}
}

};