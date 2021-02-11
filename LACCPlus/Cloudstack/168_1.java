//,temp,SAMLUtils.java,326,338,temp,SAMLUtils.java,308,319
//,3
public class xxx {
    public static PublicKey decodePublicKey(String publicKey) {
        byte[] sigBytes = org.bouncycastle.util.encoders.Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(sigBytes);
        KeyFactory keyFactory = CertUtils.getKeyFactory();
        if (keyFactory == null)
            return null;
        try {
            return keyFactory.generatePublic(x509KeySpec);
        } catch (InvalidKeySpecException e) {
            s_logger.error("Unable to create PublicKey from PublicKey string:" + e.getMessage());
        }
        return null;
    }

};