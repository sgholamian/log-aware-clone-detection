//,temp,SettlementBatchSummaryGatewayIT.java,41,49,temp,TransactionGatewayIT.java,402,410
//,2
public class xxx {
    @Disabled
    @Test
    public void testCredit() throws Exception {
        // using com.braintreegateway.TransactionRequest message body for single parameter "request"
        final com.braintreegateway.Result result = requestBody("direct://CREDIT", null);

        assertNotNull(result, "credit result");
        LOG.debug("credit: " + result);
    }

};