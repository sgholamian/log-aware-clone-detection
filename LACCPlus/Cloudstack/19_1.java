//,temp,S3Utils.java,139,143,temp,S3Utils.java,127,131
//,3
public class xxx {
    public static Upload putObject(final ClientOptions clientOptions, final PutObjectRequest req) {
        LOGGER.debug(format("Sending stream as S3 object %1$s in bucket %2$s using PutObjectRequest", req.getKey(), req.getBucketName()));

        return getTransferManager(clientOptions).upload(req);
    }

};