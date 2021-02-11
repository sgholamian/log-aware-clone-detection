//,temp,ConsoleProxyPasswordBasedEncryptor.java,83,113,temp,ConsoleProxyPasswordBasedEncryptor.java,50,81
//,3
public class xxx {
    public String encryptText(String text) {
        if (text == null || text.isEmpty())
            return text;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(keyIvPair.getKeyBytes(), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(keyIvPair.getIvBytes()));

            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.encodeBase64URLSafeString(encryptedBytes);
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