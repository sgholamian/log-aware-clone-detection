//,temp,AWS2S3Producer.java,251,344,temp,AWS2S3Producer.java,134,249
//,3
public class xxx {
    public void processSingleOp(final Exchange exchange) throws Exception {

        Map<String, String> objectMetadata = determineMetadata(exchange);

        File filePayload = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        Object obj = exchange.getIn().getMandatoryBody();
        PutObjectRequest.Builder putObjectRequest = PutObjectRequest.builder();
        // Need to check if the message body is WrappedFile
        if (obj instanceof WrappedFile) {
            obj = ((WrappedFile<?>) obj).getFile();
        }
        if (obj instanceof File) {
            filePayload = (File) obj;
            is = new FileInputStream(filePayload);
        } else {
            is = exchange.getIn().getMandatoryBody(InputStream.class);
            if (objectMetadata.containsKey(Exchange.CONTENT_LENGTH)) {
                if (objectMetadata.get("Content-Length").equals("0")
                        && ObjectHelper.isEmpty(exchange.getProperty(Exchange.CONTENT_LENGTH))) {
                    LOG.debug("The content length is not defined. It needs to be determined by reading the data into memory");
                    baos = AWS2S3Utils.determineLengthInputStream(is);
                    objectMetadata.put("Content-Length", String.valueOf(baos.size()));
                    is = new ByteArrayInputStream(baos.toByteArray());
                } else {
                    if (ObjectHelper.isNotEmpty(exchange.getProperty(Exchange.CONTENT_LENGTH))) {
                        objectMetadata.put("Content-Length", exchange.getProperty(Exchange.CONTENT_LENGTH, String.class));
                    }
                }
            }
        }

        final String bucketName = AWS2S3Utils.determineBucketName(exchange, getConfiguration());
        final String key = AWS2S3Utils.determineKey(exchange, getConfiguration());
        putObjectRequest.bucket(bucketName).key(key).metadata(objectMetadata);

        String storageClass = AWS2S3Utils.determineStorageClass(exchange, getConfiguration());
        if (storageClass != null) {
            putObjectRequest.storageClass(storageClass);
        }

        String cannedAcl = exchange.getIn().getHeader(AWS2S3Constants.CANNED_ACL, String.class);
        if (cannedAcl != null) {
            ObjectCannedACL objectAcl = ObjectCannedACL.valueOf(cannedAcl);
            putObjectRequest.acl(objectAcl);
        }

        BucketCannedACL acl = exchange.getIn().getHeader(AWS2S3Constants.ACL, BucketCannedACL.class);
        if (acl != null) {
            // note: if cannedacl and acl are both specified the last one will
            // be used. refer to
            // PutObjectRequest#setAccessControlList for more details
            putObjectRequest.acl(acl.toString());
        }

        if (getConfiguration().isUseAwsKMS()) {
            if (ObjectHelper.isNotEmpty(getConfiguration().getAwsKMSKeyId())) {
                putObjectRequest.ssekmsKeyId(getConfiguration().getAwsKMSKeyId());
                putObjectRequest.serverSideEncryption(ServerSideEncryption.AWS_KMS);
            }
        }

        if (getConfiguration().isUseCustomerKey()) {
            if (ObjectHelper.isNotEmpty(getConfiguration().getCustomerKeyId())) {
                putObjectRequest.sseCustomerKey(getConfiguration().getCustomerKeyId());
            }
            if (ObjectHelper.isNotEmpty(getConfiguration().getCustomerKeyMD5())) {
                putObjectRequest.sseCustomerKeyMD5(getConfiguration().getCustomerKeyMD5());
            }
            if (ObjectHelper.isNotEmpty(getConfiguration().getCustomerAlgorithm())) {
                putObjectRequest.sseCustomerAlgorithm(getConfiguration().getCustomerAlgorithm());
            }
        }

        LOG.trace("Put object [{}] from exchange [{}]...", putObjectRequest, exchange);

        PutObjectResponse putObjectResult = getEndpoint().getS3Client().putObject(putObjectRequest.build(),
                RequestBody.fromBytes(SdkBytes.fromInputStream(is).asByteArray()));

        LOG.trace("Received result [{}]", putObjectResult);

        Message message = getMessageForResponse(exchange);
        message.setHeader(AWS2S3Constants.E_TAG, putObjectResult.eTag());
        if (putObjectResult.versionId() != null) {
            message.setHeader(AWS2S3Constants.VERSION_ID, putObjectResult.versionId());
        }

        IOHelper.close(is);

        if (getConfiguration().isDeleteAfterWrite() && filePayload != null) {
            FileUtil.deleteFile(filePayload);
        }
    }

};