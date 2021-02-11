//,temp,sample_8262.java,2,15,temp,sample_1805.java,2,15
//,3
public class xxx {
public static String signRequest(String request, String key) {
try {
Mac mac = Mac.getInstance("HmacSHA1");
SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
mac.init(keySpec);
mac.update(request.getBytes());
byte[] encryptedBytes = mac.doFinal();
return org.apache.commons.codec.binary.Base64.encodeBase64String(encryptedBytes);
} catch (Exception ex) {


log.info("unable to sign request");
}
}

};