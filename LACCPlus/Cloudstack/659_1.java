//,temp,S3Utils.java,145,149,temp,S3Utils.java,127,131
//,2
public class xxx {
    public static Download getFile(final ClientOptions clientOptions, final String bucketName, final String key, final File file) {
        LOGGER.debug(format("Receiving object %1$s as file %2$s from bucket %3$s", key, file.getAbsolutePath(), bucketName));

        return getTransferManager(clientOptions).download(bucketName, key, file);
    }

};