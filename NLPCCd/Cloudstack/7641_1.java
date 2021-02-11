//,temp,sample_4575.java,2,17,temp,sample_731.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (encryptedText == null || encryptedText.isEmpty()) return encryptedText;
try {
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
SecretKeySpec keySpec = new SecretKeySpec(keyIvPair.getKeyBytes(), "AES");
cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(keyIvPair.getIvBytes()));
byte[] encryptedBytes = Base64.decodeBase64(encryptedText);
return new String(cipher.doFinal(encryptedBytes));
} catch (NoSuchAlgorithmException e) {
return null;
} catch (NoSuchPaddingException e) {


log.info("unexpected exception");
}
}

};