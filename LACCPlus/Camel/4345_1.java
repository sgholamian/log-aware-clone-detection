//,temp,TransactionGatewayIT.java,309,345,temp,TransactionGatewayIT.java,105,146
//,3
public class xxx {
    @Test
    public void testRefundWithAmount() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");

        final Result<Transaction> createResult = requestBody(
                "direct://SALE",
                new TransactionRequest()
                        .amount(new BigDecimal("100.07"))
                        .paymentMethodNonce("fake-valid-nonce")
                        .options()
                        .submitForSettlement(true)
                        .done(),
                Result.class);

        LOG.info("Result message: {}", createResult.getMessage());
        assertNotNull(createResult, "sale result");
        assertTrue(createResult.isSuccess());

        String createId = createResult.getTarget().getId();

        final Result<Transaction> settleResult = this.gateway.testing().settle(createId);
        assertNotNull(settleResult, "settle result");
        assertTrue(settleResult.isSuccess());

        final Result<Transaction> result = requestBodyAndHeaders(
                "direct://REFUND",
                null,
                new BraintreeHeaderBuilder()
                        .add("id", createId)
                        .add("amount", new BigDecimal("99.00"))
                        .build(),
                Result.class);

        assertNotNull(result, "Request Refund result");
        assertTrue(result.isSuccess());
        LOG.info(String.format("Refund id(%s) created for transaction id(%s)", result.getTarget().getId(), createId));
    }

};