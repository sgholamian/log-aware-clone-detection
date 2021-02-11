//,temp,sample_725.java,2,17,temp,sample_4569.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (text == null || text.isEmpty()) return text;
try {
Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
SecretKeySpec keySpec = new SecretKeySpec(keyIvPair.getKeyBytes(), "AES");
cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(keyIvPair.getIvBytes()));
byte[] encryptedBytes = cipher.doFinal(text.getBytes());
return Base64.encodeBase64URLSafeString(encryptedBytes);
} catch (NoSuchAlgorithmException e) {
return null;
} catch (NoSuchPaddingException e) {


log.info("unexpected exception");
}
}

};