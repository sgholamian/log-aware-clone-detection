//,temp,sample_724.java,2,15,temp,sample_4568.java,2,15
//,2
public class xxx {
public String encryptText(String text) {
if (text == null || text.isEmpty()) return text;
try {
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
SecretKeySpec keySpec = new SecretKeySpec(keyIvPair.getKeyBytes(), "AES");
cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(keyIvPair.getIvBytes()));
byte[] encryptedBytes = cipher.doFinal(text.getBytes());
return Base64.encodeBase64URLSafeString(encryptedBytes);
} catch (NoSuchAlgorithmException e) {


log.info("unexpected exception");
}
}

};