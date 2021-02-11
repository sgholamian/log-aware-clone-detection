//,temp,SAMLUtils.java,345,357,temp,SAMLUtils.java,308,319
//,3
public class xxx {
    public static PrivateKey decodePrivateKey(String privateKey) {
        byte[] sigBytes = org.bouncycastle.util.encoders.Base64.decode(privateKey);
        PKCS8EncodedKeySpec pkscs8KeySpec = new PKCS8EncodedKeySpec(sigBytes);
        KeyFactory keyFactory = CertUtils.getKeyFactory();
        if (keyFactory == null)
            return null;
        try {
            return keyFactory.generatePrivate(pkscs8KeySpec);
        } catch (InvalidKeySpecException e) {
            s_logger.error("Unable to create PrivateKey from privateKey string:" + e.getMessage());
        }
        return null;
    }

};