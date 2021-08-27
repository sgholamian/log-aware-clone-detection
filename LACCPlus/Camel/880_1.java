//,temp,MailBinding.java,229,250,temp,MailBinding.java,207,227
//,3
public class xxx {
    protected String populateContentOnBodyPart(BodyPart part, MailConfiguration configuration, Exchange exchange)
            throws MessagingException, IOException {

        String contentType = determineContentType(configuration, exchange);

        if (contentType != null) {
            LOG.trace("Using Content-Type {} for BodyPart: {}", contentType, part);

            // always store content in a byte array data store to avoid various content type and charset issues
            String data = exchange.getContext().getTypeConverter().tryConvertTo(String.class, exchange.getIn().getBody());
            // use empty data if the body was null for some reason (otherwise there is a NPE)
            data = data != null ? data : "";

            DataSource ds = new ByteArrayDataSource(data, contentType);
            part.setDataHandler(new DataHandler(ds));

            // set the content type header afterwards
            part.setHeader("Content-Type", contentType);
        }

        return contentType;
    }

};