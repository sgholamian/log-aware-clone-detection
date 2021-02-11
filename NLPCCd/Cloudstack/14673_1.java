//,temp,sample_4574.java,2,15,temp,sample_730.java,2,15
//,2
public class xxx {
public String decryptText(String encryptedText) {
if (encryptedText == null || encryptedText.isEmpty()) return encryptedText;
try {
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
SecretKeySpec keySpec = new SecretKeySpec(keyIvPair.getKeyBytes(), "AES");
cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(keyIvPair.getIvBytes()));
byte[] encryptedBytes = Base64.decodeBase64(encryptedText);
return new String(cipher.doFinal(encryptedBytes));
} catch (NoSuchAlgorithmException e) {


log.info("unexpected exception");
}
}

};