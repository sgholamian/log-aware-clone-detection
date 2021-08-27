//,temp,sample_75.java,2,15,temp,sample_74.java,2,14
//,3
public class xxx {
public static List<PGPPublicKey> findPublicKeys(List<String> useridParts, boolean forEncryption, PGPPublicKeyRingCollection pgpPublicKeyringCollection) {
List<PGPPublicKey> result = new ArrayList<PGPPublicKey>(useridParts.size());
for (Iterator<PGPPublicKeyRing> keyRingIter = pgpPublicKeyringCollection.getKeyRings(); keyRingIter.hasNext();) {
PGPPublicKeyRing keyRing = keyRingIter.next();
PGPPublicKey primaryKey = keyRing.getPublicKey();
String[] foundKeyUserIdForUserIdPart = findFirstKeyUserIdContainingOneOfTheParts(useridParts, primaryKey);
if (foundKeyUserIdForUserIdPart == null) {
continue;
}


log.info("user id found in primary key with key id containing one of the parts");
}
}

};