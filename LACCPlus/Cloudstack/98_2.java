//,temp,StressTestDirectAttach.java,1069,1081,temp,CloudianUtils.java,43,57
//,3
public class xxx {
    public static String generateHMACSignature(final String data, final String key) {
        if (Strings. isNullOrEmpty(data) || Strings.isNullOrEmpty(key)) {
            return null;
        }
        try {
            final SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            final Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            return Base64.encodeBase64String(rawHmac);
        } catch (final Exception e) {
            LOG.error("Failed to generate HMAC signature from provided data and key, due to: ", e);
        }
        return null;
    }

};