//,temp,MllpSocketBuffer.java,354,371,temp,MllpSocketBuffer.java,284,302
//,3
public class xxx {
    public synchronized String toHl7String(String charsetName) {
        if (charsetName != null && !charsetName.isEmpty()) {
            try {
                if (Charset.isSupported(charsetName)) {
                    return toHl7String(Charset.forName(charsetName));
                }
                log.warn(
                        "toHl7String(charsetName[{}]) - unsupported character set name - using the MLLP default character set {}",
                        charsetName, MllpComponent.getDefaultCharset());
            } catch (Exception charsetEx) {
                log.warn(
                        "toHl7String(charsetName[{}]) - ignoring exception encountered determining character set for name - using the MLLP default character set {}",
                        charsetName, MllpComponent.getDefaultCharset(), charsetEx);
            }
        }

        return toHl7String(MllpComponent.getDefaultCharset());
    }

};