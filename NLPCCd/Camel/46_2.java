//,temp,sample_2931.java,2,13,temp,sample_3442.java,2,16
//,3
public class xxx {
public void dummy_method(){
String bucketName = getConfiguration().getBucketName();
String prefix = getConfiguration().getPrefix();
try {
s3Client.listObjects(new ListObjectsRequest(bucketName, prefix, null, null, 0));
return;
} catch (AmazonServiceException ase) {
if (ase.getStatusCode() != 404) {
throw ase;
}
}


log.info("bucket doesn t exist yet");
}

};