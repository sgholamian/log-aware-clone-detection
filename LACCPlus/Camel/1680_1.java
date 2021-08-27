//,temp,TransactionGatewayIT.java,148,174,temp,TransactionGatewayIT.java,83,103
//,3
public class xxx {
    @Test
    public void testFind() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");

        final Result<Transaction> createResult = requestBody(
                "direct://SALE",
                new TransactionRequest()
                        .amount(new BigDecimal("100.02"))
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

        // using String message body for single parameter "id"
        final Transaction result = requestBody("direct://FIND", createResult.getTarget().getId());

        assertNotNull(result, "find result");
        LOG.info("Transaction found - id={}", result.getId());
    }

};