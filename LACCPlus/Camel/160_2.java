//,temp,SettlementBatchSummaryGatewayIT.java,52,65,temp,TransactionGatewayIT.java,446,459
//,2
public class xxx {
    @Disabled
    @Test
    public void testSubmitForPartialSettlement() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelBraintree.id", null);
        // parameter type is java.math.BigDecimal
        headers.put("CamelBraintree.amount", null);

        final com.braintreegateway.Result result = requestBodyAndHeaders("direct://SUBMITFORPARTIALSETTLEMENT", null, headers);

        assertNotNull(result, "submitForPartialSettlement result");
        LOG.debug("submitForPartialSettlement: " + result);
    }

};