//,temp,ConsoleProxyPasswordBasedEncryptor.java,83,113,temp,ConsoleProxyPasswordBasedEncryptor.java,50,81
//,3
public class xxx {
    public String decryptText(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty())
            return encryptedText;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyIvPair.getKeyBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(keyIvPair.getIvBytes()));

            byte[] encryptedBytes = Base64.decodeBase64(encryptedText);
            return new String(cipher.doFinal(encryptedBytes));
        } catch (NoSuchAlgorithmException e) {
            s_logger.error("Unexpected exception ", e);
            return null;
        } catch (NoSuchPaddingException e) {
            s_logger.error("Unexpected exception ", e);
            return null;
        } catch (IllegalBlockSizeException e) {
            s_logger.error("Unexpected exception ", e);
            return null;
        } catch (BadPaddingException e) {
            s_logger.error("Unexpected exception ", e);
            return null;
        } catch (InvalidKeyException e) {
            s_logger.error("Unexpected exception ", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            s_logger.error("Unexpected exception ", e);
            return null;
        }
    }

};