//,temp,RegionsApiUtil.java,297,309,temp,EncryptionUtil.java,60,72
//,3
public class xxx {
    public static String generateSignature(String data, String key) {
        try {
            final Mac mac = Mac.getInstance("HmacSHA1");
            final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            mac.init(keySpec);
            mac.update(data.getBytes("UTF-8"));
            final byte[] encryptedBytes = mac.doFinal();
            return Base64.encodeBase64String(encryptedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
            s_logger.error("exception occurred which encoding the data." + e.getMessage());
            throw new CloudRuntimeException("unable to generate signature", e);
        }
    }

};