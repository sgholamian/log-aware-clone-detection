//,temp,PGPDataFormatUtil.java,288,328,temp,PGPDataFormatUtil.java,183,219
//,3
public class xxx {
    public static List<PGPPublicKey> findPublicKeys(
            List<String> useridParts, boolean forEncryption, PGPPublicKeyRingCollection pgpPublicKeyringCollection) {
        List<PGPPublicKey> result = new ArrayList<>(useridParts.size());
        for (Iterator<PGPPublicKeyRing> keyRingIter = pgpPublicKeyringCollection.getKeyRings(); keyRingIter.hasNext();) {
            PGPPublicKeyRing keyRing = keyRingIter.next();
            PGPPublicKey primaryKey = keyRing.getPublicKey();
            String[] foundKeyUserIdForUserIdPart = findFirstKeyUserIdContainingOneOfTheParts(useridParts, primaryKey);
            if (foundKeyUserIdForUserIdPart == null) {
                LOG.debug("No User ID found in primary key with key ID {} containing one of the parts {}",
                        primaryKey.getKeyID(),
                        useridParts);
                continue;
            }
            LOG.debug("User ID {} found in primary key with key ID {} containing one of the parts {}",
                    foundKeyUserIdForUserIdPart[0], primaryKey.getKeyID(), useridParts);
            // add adequate keys to the result
            for (Iterator<PGPPublicKey> keyIter = keyRing.getPublicKeys(); keyIter.hasNext();) {
                PGPPublicKey key = keyIter.next();
                if (forEncryption) {
                    if (isEncryptionKey(key)) {
                        LOG.debug("Public encryption key with key user ID {} and key ID {} added to the encryption keys",
                                foundKeyUserIdForUserIdPart[0], Long.toString(key.getKeyID()));
                        result.add(key);
                    }
                } else if (!forEncryption && isSignatureKey(key)) {
                    // not used!
                    result.add(key);
                    LOG.debug("Public key with key user ID {} and key ID {} added to the signing keys",
                            foundKeyUserIdForUserIdPart[0],
                            Long.toString(key.getKeyID()));
                }
            }

        }

        return result;
    }

};