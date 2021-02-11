//,temp,S3Utils.java,164,168,temp,S3Utils.java,157,161
//,3
public class xxx {
    public static URL generatePresignedUrl(final ClientOptions clientOptions, final String bucketName, final String key, final Date expiration) {
        LOGGER.debug(format("Generating presigned url for key %1s in bucket %2s with expiration date %3s", key, bucketName, expiration.toString()));

        return getTransferManager(clientOptions).getAmazonS3Client().generatePresignedUrl(bucketName, key, expiration, HttpMethod.GET);
    }

};