//,temp,TestClientWithAPI.java,1999,2011,temp,StressTestDirectAttach.java,1069,1081
//,3
public class xxx {
    public static String signRequest(String request, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            mac.update(request.getBytes());
            byte[] encryptedBytes = mac.doFinal();
            return Base64.encodeBase64String(encryptedBytes);
        } catch (Exception ex) {
            s_logger.error("unable to sign request", ex);
        }
        return null;
    }

};