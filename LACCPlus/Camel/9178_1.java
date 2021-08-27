//,temp,MllpConfiguration.java,234,252,temp,MllpComponent.java,184,201
//,3
public class xxx {
    public Charset getCharset(Exchange exchange) {
        String exchangeCharsetName = ExchangeHelper.getCharsetName(exchange, false);
        if (exchangeCharsetName != null && !exchangeCharsetName.isEmpty()) {
            try {
                if (Charset.isSupported(exchangeCharsetName)) {
                    return Charset.forName(exchangeCharsetName);
                }
                LOG.warn(
                        "Unsupported character set name '{}' specified in the Exchange - checking for configured character set",
                        exchangeCharsetName);
            } catch (Exception charsetEx) {
                LOG.warn(
                        "Ignoring exception determining character set for name '{}' specified in the Exchange - checking for configured character set",
                        exchangeCharsetName, charsetEx);
            }
        }

        return getCharset();
    }

};