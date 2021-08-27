//,temp,sample_2934.java,2,17,temp,sample_2935.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (marker != null) {
listObjectsRequest.setMarker(marker);
}
ObjectListing listObjects = getAmazonS3Client().listObjects(listObjectsRequest);
if (listObjects.isTruncated()) {
marker = listObjects.getNextMarker();
} else {
marker = null;
}
if (LOG.isTraceEnabled()) {


log.info("found objects in bucket");
}
}

};