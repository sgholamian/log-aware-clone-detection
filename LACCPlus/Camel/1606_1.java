//,temp,MllpConfiguration.java,216,232,temp,MllpConfiguration.java,197,214
//,3
public class xxx {
    public Charset getCharset() {
        if (hasCharsetName()) {
            try {
                if (Charset.isSupported(charsetName)) {
                    return Charset.forName(charsetName);
                }
                LOG.warn("Unsupported character set name '{}' configured for the MLLP Endpoint - returning default charset {}",
                        charsetName, MllpComponent.getDefaultCharset());
            } catch (Exception charsetEx) {
                LOG.warn(
                        "Ignoring exception determining character set for name '{}' configured for the MLLP Endpoint - returning default charset {}",
                        charsetName, MllpComponent.getDefaultCharset(), charsetEx);
            }
        }

        return MllpComponent.getDefaultCharset();
    }

};