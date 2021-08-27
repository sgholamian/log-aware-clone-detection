//,temp,DisputeGatewayIT.java,212,247,temp,DisputeGatewayIT.java,154,177
//,3
public class xxx {
    @Test
    public void testRemoveEvidence() throws Exception {
        final String textEvidence = "Text Evidence";

        Dispute createdDispute = createDispute();
        assertEquals(Dispute.Status.OPEN, createdDispute.getStatus());

        final Map<String, Object> addTextEvidenceHeaders = new HashMap<>();
        addTextEvidenceHeaders.put("CamelBraintree.id", createdDispute.getId());
        addTextEvidenceHeaders.put("CamelBraintree.content", textEvidence);

        final Result<DisputeEvidence> addTextEvidenceResult = requestBodyAndHeaders(
                "direct://ADDTEXTEVIDENCE",
                null,
                addTextEvidenceHeaders);

        LOG.info("Result message: {}", addTextEvidenceResult.getMessage());
        assertNotNull(addTextEvidenceResult, "addTextEvidence result");
        assertTrue(addTextEvidenceResult.isSuccess(), "addTextEvidence result success");

        DisputeEvidence disputeEvidence = addTextEvidenceResult.getTarget();
        assertEquals(textEvidence, disputeEvidence.getComment());

        final Map<String, Object> removeTextEvidenceHeaders = new HashMap<>();
        removeTextEvidenceHeaders.put("CamelBraintree.disputeId", createdDispute.getId());
        removeTextEvidenceHeaders.put("CamelBraintree.evidenceId", disputeEvidence.getId());

        final Result removeTextEvidenceResult = requestBodyAndHeaders(
                "direct://REMOVEEVIDENCE",
                null,
                removeTextEvidenceHeaders);

        LOG.info("Result message: {}", removeTextEvidenceResult.getMessage());
        assertNotNull(removeTextEvidenceResult, "removeEvidence result");
        assertTrue(removeTextEvidenceResult.isSuccess(), "removeEvidence result success");
    }

};