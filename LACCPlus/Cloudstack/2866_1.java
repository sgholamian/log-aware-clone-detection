//,temp,DBEncryptionUtil.java,53,69,temp,DBEncryptionUtil.java,36,51
//,2
public class xxx {
    public static String decrypt(String encrypted) {
        if (!EncryptionSecretKeyChecker.useEncryption() || (encrypted == null) || encrypted.isEmpty()) {
            return encrypted;
        }
        if (s_encryptor == null) {
            initialize();
        }

        String plain = null;
        try {
            plain = s_encryptor.decrypt(encrypted);
        } catch (EncryptionOperationNotPossibleException e) {
            s_logger.debug("Error while decrypting: " + encrypted);
            throw e;
        }
        return plain;
    }

};