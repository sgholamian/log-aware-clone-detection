//,temp,sample_8511.java,2,17,temp,sample_8513.java,2,16
//,3
public class xxx {
public void dummy_method(){
FederationStateStoreClientMetrics .succeededStateStoreCall(stopTime - startTime);
if (subClusterId.equals(subClusterIdHome)) {
if (cstmt.getInt(4) == 0) {
String errMsg = "The application " + appId + " was not insert into the StateStore";
FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
}
if (cstmt.getInt(4) != 1) {
String errMsg = "Wrong behavior during the insertion of SubCluster " + subClusterId;
FederationStateStoreUtils.logAndThrowStoreException(LOG, errMsg);
}


log.info("insert into the statestore the application in subcluster");
}
}

};