//,temp,S3Utils.java,145,149,temp,S3Utils.java,133,137
//,3
public class xxx {
    public static Upload putObject(final ClientOptions clientOptions, final InputStream sourceStream, final String bucketName, final String key) {
        LOGGER.debug(format("Sending stream as S3 object %1$s in bucket %2$s", key, bucketName));

        return getTransferManager(clientOptions).upload(bucketName, key, sourceStream, null);
    }

};