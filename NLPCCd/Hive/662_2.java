//,temp,sample_191.java,2,10,temp,sample_3863.java,2,9
//,3
public class xxx {
public synchronized void removeTokenForJob(String tokenIdentifier) {
int[] refCount = tokenRefMap.get(tokenIdentifier);
if (refCount == null) {


log.info("no refcount found for tokenidentifier");
}
}

};