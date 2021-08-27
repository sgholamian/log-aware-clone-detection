//,temp,sample_2934.java,2,17,temp,sample_2935.java,2,17
//,3
public class xxx {
public void dummy_method(){
listObjectsRequest.setPrefix(getConfiguration().getPrefix());
if (maxMessagesPerPoll > 0) {
listObjectsRequest.setMaxKeys(maxMessagesPerPoll);
}
if (marker != null) {
listObjectsRequest.setMarker(marker);
}
ObjectListing listObjects = getAmazonS3Client().listObjects(listObjectsRequest);
if (listObjects.isTruncated()) {
marker = listObjects.getNextMarker();


log.info("returned list is truncated so setting next marker");
}
}

};