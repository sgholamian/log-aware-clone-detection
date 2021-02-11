//,temp,ManagementServerImpl.java,3373,3390,temp,StressTestDirectAttach.java,1069,1081
//,3
public class xxx {
    private String signRequest(final String request, final String key) {
        try {
            s_logger.info("Request: " + request);
            s_logger.info("Key: " + key);

            if (key != null && request != null) {
                final Mac mac = Mac.getInstance("HmacSHA1");
                final SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
                mac.init(keySpec);
                mac.update(request.getBytes());
                final byte[] encryptedBytes = mac.doFinal();
                return new String(Base64.encodeBase64(encryptedBytes));
            }
        } catch (final Exception ex) {
            s_logger.error("unable to sign request", ex);
        }
        return null;
    }

};