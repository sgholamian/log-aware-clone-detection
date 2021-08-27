//,temp,DisputeGatewayIT.java,212,247,temp,DisputeGatewayIT.java,154,177
//,3
public class xxx {
    @Test
    public void testAddTextEvidenceOne() throws Exception {
        final String textEvidence = "Text Evidence";

        Dispute createdDispute = createDispute();
        assertEquals(Dispute.Status.OPEN, createdDispute.getStatus());

        final Map<String, Object> headers = new HashMap<>();
        headers.put("CamelBraintree.id", createdDispute.getId());
        TextEvidenceRequest textEvidenceRequest = new TextEvidenceRequest().content(textEvidence);
        headers.put("CamelBraintree.textEvidenceRequest", textEvidenceRequest);

        final Result<DisputeEvidence> result = requestBodyAndHeaders(
                "direct://ADDTEXTEVIDENCE_1",
                null,
                headers);

        LOG.info("Result message: {}", result.getMessage());
        assertNotNull(result, "addTextEvidence result");
        assertTrue(result.isSuccess(), "addTextEvidence result success");

        DisputeEvidence disputeEvidence = result.getTarget();
        assertEquals(textEvidence, disputeEvidence.getComment());
    }

};