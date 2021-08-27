//,temp,MllpConfiguration.java,234,252,temp,MllpComponent.java,184,201
//,3
public class xxx {
    public static void setDefaultCharset(String defaultCharacterSetName) {
        if (defaultCharacterSetName != null && !defaultCharacterSetName.isEmpty()) {
            try {
                if (Charset.isSupported(defaultCharacterSetName)) {
                    MllpComponent.defaultCharset = Charset.forName(defaultCharacterSetName);
                } else {
                    log.warn(
                            "Unsupported character set name '{}' in system property {} - continuing to use character set {} as default",
                            defaultCharacterSetName, MllpComponent.MLLP_DEFAULT_CHARSET_PROPERTY, defaultCharset);
                }
            } catch (Exception charsetEx) {
                MllpComponent.defaultCharset = StandardCharsets.ISO_8859_1;
                log.warn(
                        "Exception encountered determining character set for '{}' - continuing to use character set {} as default",
                        defaultCharacterSetName, defaultCharset);
            }
        }
    }

};