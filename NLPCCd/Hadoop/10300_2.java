//,temp,sample_8511.java,2,17,temp,sample_8513.java,2,16
//,3
public class xxx {
public void dummy_method(){
cstmt.executeUpdate();
long stopTime = clock.getTime();
if (cstmt.getInt(3) == 0) {
String errMsg = "Application " + appId + " does not exist";
FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
}
if (cstmt.getInt(3) != 1) {
String errMsg = "Wrong behavior during the update of SubCluster " + subClusterId;
FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
}


log.info("update the subcluster to for application in the statestore");
}

};