//,temp,TransactionGatewayIT.java,148,174,temp,TransactionGatewayIT.java,83,103
//,3
public class xxx {
    @Test
    public void testSale() throws Exception {
        assertNotNull(this.gateway, "BraintreeGateway can't be null");

        final Result<Transaction> result = requestBody(
                "direct://SALE",
                new TransactionRequest()
                        .amount(new BigDecimal("100.00"))
                        .paymentMethodNonce("fake-valid-nonce")
                        .options()
                        .submitForSettlement(true)
                        .done(),
                Result.class);

        LOG.info("Result message: {}", result.getMessage());
        assertNotNull(result, "sale result");
        assertTrue(result.isSuccess());

        LOG.info("Transaction done - id={}", result.getTarget().getId());
        this.transactionIds.add(result.getTarget().getId());
    }

};