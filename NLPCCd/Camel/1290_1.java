//,temp,sample_8031.java,2,13,temp,sample_1499.java,2,16
//,3
public class xxx {
public void testUsingCustomExceptionHandlerAndOneRedelivery() throws Exception {
a.expectedMessageCount(1);
sendBody("direct:start", "a");
MockEndpoint.assertIsSatisfied(a, b);
List<Exchange> list = a.getReceivedExchanges();
assertTrue("List should not be empty!", !list.isEmpty());
Exchange exchange = list.get(0);
Message in = exchange.getIn();


log.info("found message with headers");
}

};