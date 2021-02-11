//,temp,SAMLUtils.java,308,319,temp,SAMLUtils.java,291,301
//,2
public class xxx {
    public static String encodePublicKey(PublicKey key) {
        try {
            KeyFactory keyFactory = CertUtils.getKeyFactory();
            if (keyFactory == null) return null;
            X509EncodedKeySpec spec = keyFactory.getKeySpec(key, X509EncodedKeySpec.class);
            return new String(org.bouncycastle.util.encoders.Base64.encode(spec.getEncoded()), Charset.forName("UTF-8"));
        } catch (InvalidKeySpecException e) {
            s_logger.error("Unable to get KeyFactory:" + e.getMessage());
        }
        return null;
    }

};