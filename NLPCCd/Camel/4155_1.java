//,temp,sample_3440.java,2,16,temp,sample_3439.java,2,14
//,3
public class xxx {
public void dummy_method(){
super.doStart();
s3Client = configuration.getAmazonS3Client() != null ? configuration.getAmazonS3Client() : createS3Client();
if (ObjectHelper.isNotEmpty(configuration.getAmazonS3Endpoint())) {
s3Client.setEndpoint(configuration.getAmazonS3Endpoint());
}
String fileName = getConfiguration().getFileName();
if (fileName != null) {
return;
}
String bucketName = getConfiguration().getBucketName();


log.info("querying whether bucket already exists");
}

};