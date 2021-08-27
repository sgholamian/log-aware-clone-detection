//,temp,RedeliveryPolicyPerExceptionTest.java,56,74,temp,RedeliveryPolicyPerExceptionTest.java,36,54
//,2
public class xxx {
    @Test
    public void testUsingCustomExceptionHandlerWithNoRedeliveries() throws Exception {
        b.expectedMessageCount(1);

        sendBody("direct:start", "b");

        MockEndpoint.assertIsSatisfied(a, b);

        List<Exchange> list = b.getReceivedExchanges();
        boolean b1 = !list.isEmpty();
        assertTrue(b1, "List should not be empty!");
        Exchange exchange = list.get(0);
        Message in = exchange.getIn();
        log.info("Found message with headers: " + in.getHeaders());

        assertMessageHeader(in, Exchange.REDELIVERY_COUNTER, 0);
        assertMessageHeader(in, Exchange.REDELIVERY_MAX_COUNTER, null);
        assertMessageHeader(in, Exchange.REDELIVERED, false);
    }

};