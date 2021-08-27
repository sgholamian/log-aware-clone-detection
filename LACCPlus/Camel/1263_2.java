//,temp,PGPDataFormatUtil.java,330,346,temp,PGPDataFormatUtil.java,221,238
//,3
public class xxx {
    private static boolean isEncryptionKey(PGPPublicKey key) {
        if (!key.isEncryptionKey()) {
            return false;
        }
        //check keyflags
        Boolean hasEncryptionKeyFlags
                = hasOneOfExpectedKeyFlags(key, new int[] { KeyFlags.ENCRYPT_COMMS, KeyFlags.ENCRYPT_STORAGE });
        if (hasEncryptionKeyFlags != null && !hasEncryptionKeyFlags) {
            LOG.debug(
                    "Public key with key key ID {} found for specified user ID. But this key will not be used for the encryption, because its key flags are not encryption key flags.",
                    Long.toString(key.getKeyID()));
            return false;
        } else {
            // also without keyflags (hasEncryptionKeyFlags = null), true is returned!
            return true;
        }

    }

};