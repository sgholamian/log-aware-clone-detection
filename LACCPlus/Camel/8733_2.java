//,temp,MllpSocketBuffer.java,354,371,temp,MllpSocketBuffer.java,284,302
//,3
public class xxx {
    public synchronized String toString(String charsetName) {
        if (availableByteCount > 0) {
            try {
                if (Charset.isSupported(charsetName)) {
                    return toString(Charset.forName(charsetName));
                }
                log.warn("toString(charsetName[{}]) - unsupported character set name - using the MLLP default character set {}",
                        charsetName, MllpComponent.getDefaultCharset());
            } catch (Exception charsetEx) {
                log.warn(
                        "toString(charsetName[{}]) - ignoring exception encountered determining character set - using the MLLP default character set {}",
                        charsetName, MllpComponent.getDefaultCharset(), charsetEx);
            }

            return toString(MllpComponent.getDefaultCharset());
        }

        return "";
    }

};