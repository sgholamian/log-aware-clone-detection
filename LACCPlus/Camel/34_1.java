//,temp,SettlementBatchSummaryGatewayIT.java,41,49,temp,TransactionGatewayIT.java,402,410
//,2
public class xxx {
    @Disabled
    @Test
    public void testGenerate() throws Exception {
        // using java.util.Calendar message body for single parameter "settlementDate"
        final com.braintreegateway.Result result = requestBody("direct://GENERATE", null);

        assertNotNull(result, "generate result");
        LOG.debug("generate: " + result);
    }

};