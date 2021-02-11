//,temp,sample_4668.java,2,13,temp,sample_182.java,2,8
//,3
public class xxx {
public boolean flattenOneSegment(long requesterVersion, CompactingMemStore.IndexType idxType, MemStoreCompactionStrategy.Action action) {
if(requesterVersion != version) {
return false;
}
synchronized (pipeline){
if(requesterVersion != version) {


log.info("segment flattening failed because versions do not match");
}
}
}

};