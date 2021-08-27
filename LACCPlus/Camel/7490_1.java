//,temp,PGPDataFormatUtil.java,288,328,temp,PGPDataFormatUtil.java,183,219
//,3
public class xxx {
    public static List<PGPSecretKeyAndPrivateKeyAndUserId> findSecretKeysWithPrivateKeyAndUserId(
            Map<String, String> sigKeyUserId2Password,
            String provider, PGPSecretKeyRingCollection pgpSec)
            throws PGPException {
        List<PGPSecretKeyAndPrivateKeyAndUserId> result = new ArrayList<>(sigKeyUserId2Password.size());
        for (Iterator<?> i = pgpSec.getKeyRings(); i.hasNext();) {
            Object data = i.next();
            if (data instanceof PGPSecretKeyRing) {
                PGPSecretKeyRing keyring = (PGPSecretKeyRing) data;
                PGPSecretKey primaryKey = keyring.getSecretKey();
                List<String> useridParts = new ArrayList<>(sigKeyUserId2Password.keySet());
                String[] foundKeyUserIdForUserIdPart
                        = findFirstKeyUserIdContainingOneOfTheParts(useridParts, primaryKey.getPublicKey());
                if (foundKeyUserIdForUserIdPart == null) {
                    LOG.debug("No User ID found in primary key with key ID {} containing one of the parts {}",
                            primaryKey.getKeyID(),
                            useridParts);
                    continue;
                }
                LOG.debug("User ID {} found in primary key with key ID {} containing one of the parts {}",
                        foundKeyUserIdForUserIdPart[0], primaryKey.getKeyID(), useridParts);
                // add all signing keys
                for (Iterator<PGPSecretKey> iterKey = keyring.getSecretKeys(); iterKey.hasNext();) {
                    PGPSecretKey secKey = iterKey.next();
                    if (isSigningKey(secKey)) {
                        PGPPrivateKey privateKey
                                = secKey.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider(provider)
                                        .build(sigKeyUserId2Password.get(foundKeyUserIdForUserIdPart[1]).toCharArray()));
                        if (privateKey != null) {
                            result.add(
                                    new PGPSecretKeyAndPrivateKeyAndUserId(secKey, privateKey, foundKeyUserIdForUserIdPart[0]));
                            LOG.debug("Private key with user ID {} and key ID {} added to the signing keys",
                                    foundKeyUserIdForUserIdPart[0], Long.toString(privateKey.getKeyID()));

                        }
                    }
                }
            }
        }
        return result;
    }

};