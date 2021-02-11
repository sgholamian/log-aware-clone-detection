//,temp,sample_2037.java,2,13,temp,sample_2038.java,2,13
//,2
public class xxx {
public static String savePublicKey(PublicKey key) {
try {
KeyFactory keyFactory = SAMLUtils.getKeyFactory();
if (keyFactory == null) return null;
X509EncodedKeySpec spec = keyFactory.getKeySpec(key, X509EncodedKeySpec.class);
return new String(org.bouncycastle.util.encoders.Base64.encode(spec.getEncoded()), Charset.forName("UTF-8"));
} catch (InvalidKeySpecException e) {


log.info("unable to create keyfactory");
}
}

};