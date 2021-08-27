//,temp,PdfProducer.java,113,125,temp,PdfProducer.java,81,103
//,3
public class xxx {
    private Object doAppend(Exchange exchange) throws IOException {
        LOG.debug("Got {} operation, going to append text to provided pdf.", pdfConfiguration.getOperation());
        String body = exchange.getIn().getBody(String.class);
        try (PDDocument document = exchange.getIn().getHeader(PDF_DOCUMENT_HEADER_NAME, PDDocument.class)) {
            if (document == null) {
                throw new IllegalArgumentException(
                        String.format("%s header is expected for append operation",
                                PDF_DOCUMENT_HEADER_NAME));
            }

            if (document.isEncrypted()) {
                document.setAllSecurityToBeRemoved(true);
            }

            ProtectionPolicy protectionPolicy = exchange.getIn().getHeader(
                    PROTECTION_POLICY_HEADER_NAME, ProtectionPolicy.class);

            appendToPdfDocument(body, document, protectionPolicy);
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream;
        }
    }

};