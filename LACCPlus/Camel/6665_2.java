//,temp,TransactionGatewayIT.java,347,384,temp,TransactionGatewayIT.java,176,204
//,3
public class xxx {
    @Test
    public void testSubmitForSettlementWithId() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");

        final Result<Transaction> createResult = requestBody(
                "direct://SALE",
                new TransactionRequest()
                        .amount(new BigDecimal("100.03"))
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

        final Result<Transaction> result = requestBody(
                "direct://SUBMITFORSETTLEMENT_WITH_ID",
                createResult.getTarget().getId(),
                Result.class);

        assertNotNull(result, "Submit For Settlement result");
        LOG.debug("Transaction submitted for settlement - id={}", result.getTarget().getId());
    }

};