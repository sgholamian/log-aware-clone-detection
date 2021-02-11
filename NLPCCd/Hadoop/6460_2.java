//,temp,sample_1712.java,2,17,temp,sample_1711.java,2,15
//,3
public class xxx {
public void free() throws StorageException {
AccessCondition accessCondition = AccessCondition.generateEmptyCondition();
accessCondition.setLeaseID(leaseID);
try {
blobWrapper.getBlob().releaseLease(accessCondition);
} catch (StorageException e) {
if ("BlobNotFound".equals(e.getErrorCode())) {
} else {


log.info("unanticipated exception when trying to free lease on");
}
}
}

};