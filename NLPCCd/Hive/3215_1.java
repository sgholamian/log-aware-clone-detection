//,temp,sample_2137.java,2,17,temp,sample_5318.java,2,17
//,2
public class xxx {
public void dummy_method(){
keyVectorSerializeWrite.serializeWrite(batch, 0);
JoinUtil.JoinResult joinResult;
if (keyVectorSerializeWrite.getHasAnyNulls()) {
joinResult = JoinUtil.JoinResult.NOMATCH;
} else {
byte[] keyBytes = currentKeyOutput.getData();
int keyLength = currentKeyOutput.getLength();
joinResult = hashMap.lookup(keyBytes, 0, keyLength, hashMapResults[0]);
}
if (LOG.isDebugEnabled()) {


log.info("batch repeated joinresult");
}
}

};