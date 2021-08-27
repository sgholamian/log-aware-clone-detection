//,temp,MailBinding.java,229,250,temp,MailBinding.java,207,227
//,3
public class xxx {
    protected String populateContentOnMimeMessage(MimeMessage part, MailConfiguration configuration, Exchange exchange)
            throws MessagingException, IOException {

        String contentType = determineContentType(configuration, exchange);

        LOG.trace("Using Content-Type {} for MimeMessage: {}", contentType, part);

        String body = exchange.getIn().getBody(String.class);
        if (body == null) {
            body = "";
        }

        // always store content in a byte array data store to avoid various content type and charset issues
        DataSource ds = new ByteArrayDataSource(body, contentType);
        part.setDataHandler(new DataHandler(ds));

        // set the content type header afterwards
        part.setHeader("Content-Type", contentType);

        return contentType;
    }

};