//,temp,AddressGatewayIT.java,118,140,temp,TransactionGatewayIT.java,240,273
//,3
public class xxx {
    @Test
    public void testSubmitForSettlementWithRequest() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");

        final Result<Transaction> createResult = requestBody(
                "direct://SALE",
                new TransactionRequest()
                        .amount(new BigDecimal("100.05"))
                        .paymentMethodNonce("fake-valid-nonce")
                        .options()
                        .submitForSettlement(false)
                        .done(),
                Result.class);

        LOG.info("Result message: {}", createResult.getMessage());
        assertNotNull(createResult, "sale result");
        assertTrue(createResult.isSuccess());

        LOG.info("Transaction done - id={}", createResult.getTarget().getId());
        this.transactionIds.add(createResult.getTarget().getId());

        final Result<Transaction> result = requestBodyAndHeaders(
                "direct://SUBMITFORSETTLEMENT_WITH_REQUEST",
                null,
                new BraintreeHeaderBuilder()
                        .add("id", createResult.getTarget().getId())
                        .add("request", new TransactionRequest()
                                .amount(new BigDecimal("100.00")))
                        .build(),
                Result.class);

        assertNotNull(result, "Submit For Settlement result");
        LOG.debug("Transaction submitted for settlement - id={}", result.getTarget().getId());
    }

};