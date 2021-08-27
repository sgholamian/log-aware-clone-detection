//,temp,sample_8032.java,2,13,temp,sample_5520.java,2,17
//,3
public class xxx {
public void testUsingCustomExceptionHandlerWithNoRedeliveries() throws Exception {
b.expectedMessageCount(1);
sendBody("direct:start", "b");
MockEndpoint.assertIsSatisfied(a, b);
List<Exchange> list = b.getReceivedExchanges();
assertTrue("List should not be empty!", !list.isEmpty());
Exchange exchange = list.get(0);
Message in = exchange.getIn();


log.info("found message with headers");
}

};