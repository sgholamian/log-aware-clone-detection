//,temp,S3Utils.java,164,168,temp,S3Utils.java,157,161
//,3
public class xxx {
    public static S3ObjectInputStream getObjectStream(final ClientOptions clientOptions, final String bucketName, final String key) {
        LOGGER.debug(format("Get S3ObjectInputStream from S3 Object %1$s in bucket %2$s", key, bucketName));

        return getTransferManager(clientOptions).getAmazonS3Client().getObject(bucketName, key).getObjectContent();
    }

};