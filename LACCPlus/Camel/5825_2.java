//,temp,DisputeGatewayIT.java,179,198,temp,DisputeGatewayIT.java,90,108
//,3
public class xxx {
    @Test
    public void testAddFileEvidence() throws Exception {
        Dispute createdDispute = createDispute();
        assertEquals(Dispute.Status.OPEN, createdDispute.getStatus());
        DocumentUpload uploadedDocument = uploadDocument();

        final Map<String, Object> headers = new HashMap<>();
        headers.put("CamelBraintree.disputeId", createdDispute.getId());
        headers.put("CamelBraintree.documentId", uploadedDocument.getId());

        final Result<DisputeEvidence> result = requestBodyAndHeaders(
                "direct://ADDFILEEVIDENCE",
                null,
                headers);

        LOG.info("Result message: {}", result.getMessage());
        assertNotNull(result, "addFileEvidence result");
        assertTrue(result.isSuccess(), "addFileEvidence result success");
    }

};