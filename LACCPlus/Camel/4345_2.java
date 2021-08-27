//,temp,TransactionGatewayIT.java,309,345,temp,TransactionGatewayIT.java,105,146
//,3
public class xxx {
    @Test
    public void testCloneTransaction() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");

        final Result<Transaction> createResult = requestBody(
                "direct://SALE",
                new TransactionRequest()
                        .amount(new BigDecimal("100.01"))
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

        final Result<Transaction> cloneResult = requestBodyAndHeaders(
                "direct://CLONETRANSACTION",
                null,
                new BraintreeHeaderBuilder()
                        .add("id", createResult.getTarget().getId())
                        .add("cloneRequest", new TransactionCloneRequest()
                                .amount(new BigDecimal("99.00"))
                                .options()
                                .submitForSettlement(true)
                                .done())
                        .build(),
                Result.class);

        assertNotNull(cloneResult, "clone result");
        assertTrue(cloneResult.isSuccess());

        LOG.info("Clone Transaction done - clonedId={}, id={}",
                createResult.getTarget().getId(), cloneResult.getTarget().getId());

        this.transactionIds.add(cloneResult.getTarget().getId());
    }

};