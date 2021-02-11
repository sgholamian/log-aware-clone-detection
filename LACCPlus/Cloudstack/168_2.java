//,temp,SAMLUtils.java,326,338,temp,SAMLUtils.java,308,319
//,3
public class xxx {
    public static String encodePrivateKey(PrivateKey key) {
        try {
            KeyFactory keyFactory = CertUtils.getKeyFactory();
            if (keyFactory == null) return null;
            PKCS8EncodedKeySpec spec = keyFactory.getKeySpec(key,
                    PKCS8EncodedKeySpec.class);
            return new String(org.bouncycastle.util.encoders.Base64.encode(spec.getEncoded()), Charset.forName("UTF-8"));
        } catch (InvalidKeySpecException e) {
            s_logger.error("Unable to get KeyFactory:" + e.getMessage());
        }
        return null;
    }

};