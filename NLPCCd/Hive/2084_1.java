//,temp,sample_3956.java,2,17,temp,sample_3992.java,2,17
//,2
public class xxx {
public void dummy_method(){
JoinUtil.JoinResult joinResult;
if (!joinColVector.noNulls && joinColVector.isNull[0]) {
joinResult = JoinUtil.JoinResult.NOMATCH;
} else {
byte[] keyBytes = vector[0];
int keyStart = start[0];
int keyLength = length[0];
joinResult = hashMap.lookup(keyBytes, keyStart, keyLength, hashMapResults[0]);
}
if (LOG.isDebugEnabled()) {


log.info("batch repeated joinresult");
}
}

};