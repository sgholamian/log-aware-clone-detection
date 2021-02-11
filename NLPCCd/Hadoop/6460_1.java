//,temp,sample_1712.java,2,17,temp,sample_1711.java,2,15
//,3
public class xxx {
public void dummy_method(){
accessCondition.setLeaseID(leaseID);
try {
blobWrapper.getBlob().releaseLease(accessCondition);
} catch (StorageException e) {
if ("BlobNotFound".equals(e.getErrorCode())) {
} else {
throw(e);
}
} finally {
leaseFreed = true;


log.info("freed lease on managed by thread");
}
}

};