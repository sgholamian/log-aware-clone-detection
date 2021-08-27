//,temp,MailBinding.java,171,205,temp,MailBinding.java,145,169
//,3
public class xxx {
    protected static String determineCharSet(MailConfiguration configuration, Exchange exchange) {

        // see if we got any content type set
        String contentType = configuration.getContentType();
        if (exchange.getIn().getHeader("contentType") != null) {
            contentType = exchange.getIn().getHeader("contentType", String.class);
        } else if (exchange.getIn().getHeader(Exchange.CONTENT_TYPE) != null) {
            contentType = exchange.getIn().getHeader(Exchange.CONTENT_TYPE, String.class);
        }

        // look for charset
        String charset = MailUtils.getCharSetFromContentType(contentType);
        if (charset != null) {
            charset = IOHelper.normalizeCharset(charset);
            if (charset != null) {
                boolean supported;
                try {
                    supported = Charset.isSupported(charset);
                } catch (IllegalCharsetNameException e) {
                    supported = false;
                }
                if (supported) {
                    return charset;
                } else if (!configuration.isIgnoreUnsupportedCharset()) {
                    return charset;
                } else if (configuration.isIgnoreUnsupportedCharset()) {
                    LOG.warn("Charset: {} is not supported and cannot be used as charset in Content-Type header.", charset);
                    return null;
                }
            }
        }

        // Using the charset header of exchange as a fall back
        return ExchangeHelper.getCharsetName(exchange, false);
    }

};