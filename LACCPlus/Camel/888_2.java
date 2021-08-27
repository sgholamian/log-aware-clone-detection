//,temp,DisputeGatewayIT.java,131,152,temp,DisputeGatewayIT.java,110,129
//,3
public class xxx {
    @Test
    public void testAddFileEvidenceOne() throws Exception {
        Dispute createdDispute = createDispute();
        assertEquals(Dispute.Status.OPEN, createdDispute.getStatus());
        DocumentUpload uploadedDocument = uploadDocument();

        final Map<String, Object> headers = new HashMap<>();
        headers.put("CamelBraintree.disputeId", createdDispute.getId());
        FileEvidenceRequest fileEvidenceRequest = new FileEvidenceRequest().documentId(uploadedDocument.getId());
        headers.put("CamelBraintree.fileEvidenceRequest", fileEvidenceRequest);

        final Result<DisputeEvidence> result = requestBodyAndHeaders(
                "direct://ADDFILEEVIDENCE_1",
                null,
                headers);

        LOG.info("Result message: {}", result.getMessage());
        assertNotNull(result, "addFileEvidence result");
        assertTrue(result.isSuccess(), "addFileEvidence result success");
    }

};