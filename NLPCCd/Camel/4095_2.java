//,temp,sample_3444.java,2,16,temp,sample_3443.java,2,16
//,3
public class xxx {
public void dummy_method(){
String prefix = getConfiguration().getPrefix();
try {
s3Client.listObjects(new ListObjectsRequest(bucketName, prefix, null, null, 0));
return;
} catch (AmazonServiceException ase) {
if (ase.getStatusCode() != 404) {
throw ase;
}
}
CreateBucketRequest createBucketRequest = new CreateBucketRequest(getConfiguration().getBucketName());


log.info("creating bucket in region with request");
}

};