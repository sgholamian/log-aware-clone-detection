//,temp,RegionsApiUtil.java,297,309,temp,StressTestDirectAttach.java,1069,1081
//,3
public class xxx {
    private static String signRequest(String request, String key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(keySpec);
            mac.update(request.getBytes());
            byte[] encryptedBytes = mac.doFinal();
            return URLEncoder.encode(Base64.encodeBase64String(encryptedBytes), "UTF-8");
        } catch (Exception ex) {
            s_logger.error(ex.getMessage());
            return null;
        }
    }

};