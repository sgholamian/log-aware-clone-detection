//,temp,S3Utils.java,139,143,temp,S3Utils.java,127,131
//,3
public class xxx {
    public static Upload putFile(final ClientOptions clientOptions, final File sourceFile, final String bucketName, final String key) {
        LOGGER.debug(format("Sending file %1$s as S3 object %2$s in bucket %3$s", sourceFile.getName(), key, bucketName));

        return getTransferManager(clientOptions).upload(bucketName, key, sourceFile);
    }

};