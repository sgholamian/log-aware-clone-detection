//,temp,MailBinding.java,171,205,temp,MailBinding.java,145,169
//,3
public class xxx {
    protected String determineContentType(MailConfiguration configuration, Exchange exchange) {
        // see if we got any content type set
        String contentType = configuration.getContentType();
        if (exchange.getIn().getHeader("contentType") != null) {
            contentType = exchange.getIn().getHeader("contentType", String.class);
        } else if (exchange.getIn().getHeader(Exchange.CONTENT_TYPE) != null) {
            contentType = exchange.getIn().getHeader(Exchange.CONTENT_TYPE, String.class);
        }

        // fix content-type to have space after semi colons, otherwise some mail servers will choke
        if (contentType != null && contentType.contains(";")) {
            contentType = MailUtils.padContentType(contentType);
        }

        if (contentType != null) {
            // no charset in content-type, then try to see if we can determine one
            String charset = determineCharSet(configuration, exchange);
            // must replace charset, even with null in case its an unsupported charset
            contentType = MailUtils.replaceCharSet(contentType, charset);
        }

        LOG.trace("Determined Content-Type: {}", contentType);

        return contentType;
    }

};