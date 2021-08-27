//,temp,DisputeGatewayIT.java,179,198,temp,DisputeGatewayIT.java,90,108
//,3
public class xxx {
    @Test
    public void testFinalize() throws Exception {
        Dispute createdDispute = createDispute();
        assertEquals(Dispute.Status.OPEN, createdDispute.getStatus());

        final Result result = requestBody(
                "direct://FINALIZE",
                createdDispute.getId());

        LOG.info("Result message: {}", result.getMessage());
        assertNotNull(result, "finalize result");
        assertTrue(result.isSuccess(), "finalize result success");

        final Dispute finalizedDispute = requestBody(
                "direct://FIND",
                createdDispute.getId());

        assertNotNull(finalizedDispute, "finalized dispute");
        assertEquals(Dispute.Status.DISPUTED, finalizedDispute.getStatus());
    }

};