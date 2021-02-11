//,temp,DBEncryptionUtil.java,53,69,temp,DBEncryptionUtil.java,36,51
//,2
public class xxx {
    public static String encrypt(String plain) {
        if (!EncryptionSecretKeyChecker.useEncryption() || (plain == null) || plain.isEmpty()) {
            return plain;
        }
        if (s_encryptor == null) {
            initialize();
        }
        String encryptedString = null;
        try {
            encryptedString = s_encryptor.encrypt(plain);
        } catch (EncryptionOperationNotPossibleException e) {
            s_logger.debug("Error while encrypting: " + plain);
            throw e;
        }
        return encryptedString;
    }

};