//,temp,S3Utils.java,151,155,temp,S3Utils.java,145,149
//,3
public class xxx {
    public static Download getFile(final ClientOptions clientOptions, final GetObjectRequest getObjectRequest, final File file) {
        LOGGER.debug(format("Receiving object %1$s as file %2$s from bucket %3$s using GetObjectRequest", getObjectRequest.getKey(), file.getAbsolutePath(), getObjectRequest.getBucketName()));

        return getTransferManager(clientOptions).download(getObjectRequest, file);
    }

};