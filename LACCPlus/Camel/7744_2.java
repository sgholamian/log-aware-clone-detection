//,temp,RedeliveryPolicyPerExceptionTest.java,56,74,temp,RedeliveryPolicyPerExceptionTest.java,36,54
//,2
public class xxx {
    @Test
    public void testUsingCustomExceptionHandlerAndOneRedelivery() throws Exception {
        a.expectedMessageCount(1);

        sendBody("direct:start", "a");

        MockEndpoint.assertIsSatisfied(a, b);

        List<Exchange> list = a.getReceivedExchanges();
        boolean b1 = !list.isEmpty();
        assertTrue(b1, "List should not be empty!");
        Exchange exchange = list.get(0);
        Message in = exchange.getIn();
        log.info("Found message with headers: " + in.getHeaders());

        assertMessageHeader(in, Exchange.REDELIVERY_COUNTER, 2);
        assertMessageHeader(in, Exchange.REDELIVERY_MAX_COUNTER, 2);
        assertMessageHeader(in, Exchange.REDELIVERED, true);
    }

};