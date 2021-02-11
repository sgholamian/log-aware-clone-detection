//,temp,sample_6506.java,2,12,temp,sample_8407.java,2,11
//,3
public class xxx {
public UseSharedCacheResourceResponse use( UseSharedCacheResourceRequest request) throws YarnException, IOException {
UseSharedCacheResourceResponse response = recordFactory.newRecordInstance(UseSharedCacheResourceResponse.class);
UserGroupInformation callerUGI;
try {
callerUGI = UserGroupInformation.getCurrentUser();
} catch (IOException ie) {


log.info("error getting ugi");
}
}

};