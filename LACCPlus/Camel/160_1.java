//,temp,SettlementBatchSummaryGatewayIT.java,52,65,temp,TransactionGatewayIT.java,446,459
//,2
public class xxx {
    @Disabled
    @Test
    public void testGenerateWithCustomFields() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is java.util.Calendar
        headers.put("CamelBraintree.settlementDate", null);
        // parameter type is String
        headers.put("CamelBraintree.groupByCustomField", null);

        final com.braintreegateway.Result result = requestBodyAndHeaders("direct://GENERATE_1", null, headers);

        assertNotNull(result, "generate result");
        LOG.debug("generate: " + result);
    }

};