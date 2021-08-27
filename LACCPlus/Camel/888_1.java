//,temp,DisputeGatewayIT.java,131,152,temp,DisputeGatewayIT.java,110,129
//,3
public class xxx {
    @Test
    public void testAddTextEvidence() throws Exception {
        final String textEvidence = "Text Evidence";

        Dispute createdDispute = createDispute();
        assertEquals(Dispute.Status.OPEN, createdDispute.getStatus());

        final Map<String, Object> headers = new HashMap<>();
        headers.put("CamelBraintree.id", createdDispute.getId());
        headers.put("CamelBraintree.content", textEvidence);
        final Result<DisputeEvidence> result = requestBodyAndHeaders(
                "direct://ADDTEXTEVIDENCE",
                null,
                headers);

        LOG.info("Result message: {}", result.getMessage());
        assertNotNull(result, "addTextEvidence result");
        assertTrue(result.isSuccess(), "addTextEvidence result success");

        DisputeEvidence disputeEvidence = result.getTarget();
        assertEquals(textEvidence, disputeEvidence.getComment());
    }

};