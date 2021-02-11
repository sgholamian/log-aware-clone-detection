//,temp,S3AFileSystem.java,501,505,temp,PathOutputCommitter.java,71,76
//,3
public class xxx {
  protected void setAmazonS3Client(AmazonS3 client) {
    Preconditions.checkNotNull(client, "client");
    LOG.debug("Setting S3 client to {}", client);
    s3 = client;
  }

};