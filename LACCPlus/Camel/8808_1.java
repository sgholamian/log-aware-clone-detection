//,temp,PdfProducer.java,113,125,temp,PdfProducer.java,81,103
//,3
public class xxx {
    private OutputStream doCreate(Exchange exchange) throws IOException {
        LOG.debug("Got {} operation, going to create and write provided string to pdf document.",
                pdfConfiguration.getOperation());
        String body = exchange.getIn().getBody(String.class);
        try (PDDocument document = new PDDocument()) {
            StandardProtectionPolicy protectionPolicy = exchange.getIn().getHeader(
                    PROTECTION_POLICY_HEADER_NAME, StandardProtectionPolicy.class);
            appendToPdfDocument(body, document, protectionPolicy);
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream;
        }
    }

};