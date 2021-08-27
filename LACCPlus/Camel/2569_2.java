//,temp,AWS2S3Producer.java,251,344,temp,AWS2S3Producer.java,134,249
//,3
public class xxx {
    public void processMultiPart(final Exchange exchange) throws Exception {
        File filePayload = null;
        Object obj = exchange.getIn().getMandatoryBody();
        // Need to check if the message body is WrappedFile
        if (obj instanceof WrappedFile) {
            obj = ((WrappedFile<?>) obj).getFile();
        }
        if (obj instanceof File) {
            filePayload = (File) obj;
        } else {
            throw new IllegalArgumentException("aws2-s3: MultiPart upload requires a File input.");
        }

        Map<String, String> objectMetadata = determineMetadata(exchange);
        if (objectMetadata.containsKey("Content-Length")) {
            if (objectMetadata.get("Content-Length").equalsIgnoreCase("0")) {
                objectMetadata.put("Content-Length", String.valueOf(filePayload.length()));
            }
        } else {
            objectMetadata.put("Content-Length", String.valueOf(filePayload.length()));
        }

        final String keyName = AWS2S3Utils.determineKey(exchange, getConfiguration());
        CreateMultipartUploadRequest.Builder createMultipartUploadRequest
                = CreateMultipartUploadRequest.builder().bucket(getConfiguration().getBucketName()).key(keyName);

        String storageClass = AWS2S3Utils.determineStorageClass(exchange, getConfiguration());
        if (storageClass != null) {
            createMultipartUploadRequest.storageClass(storageClass);
        }

        String cannedAcl = exchange.getIn().getHeader(AWS2S3Constants.CANNED_ACL, String.class);
        if (cannedAcl != null) {
            ObjectCannedACL objectAcl = ObjectCannedACL.valueOf(cannedAcl);
            createMultipartUploadRequest.acl(objectAcl);
        }

        BucketCannedACL acl = exchange.getIn().getHeader(AWS2S3Constants.ACL, BucketCannedACL.class);
        if (acl != null) {
            // note: if cannedacl and acl are both specified the last one will
            // be used. refer to
            // PutObjectRequest#setAccessControlList for more details
            createMultipartUploadRequest.acl(acl.toString());
        }

        if (getConfiguration().isUseAwsKMS()) {
            createMultipartUploadRequest.ssekmsKeyId(getConfiguration().getAwsKMSKeyId());
            createMultipartUploadRequest.serverSideEncryption(ServerSideEncryption.AWS_KMS);
        }

        if (getConfiguration().isUseCustomerKey()) {
            if (ObjectHelper.isNotEmpty(getConfiguration().getCustomerKeyId())) {
                createMultipartUploadRequest.sseCustomerKey(getConfiguration().getCustomerKeyId());
            }
            if (ObjectHelper.isNotEmpty(getConfiguration().getCustomerKeyMD5())) {
                createMultipartUploadRequest.sseCustomerKeyMD5(getConfiguration().getCustomerKeyMD5());
            }
            if (ObjectHelper.isNotEmpty(getConfiguration().getCustomerAlgorithm())) {
                createMultipartUploadRequest.sseCustomerAlgorithm(getConfiguration().getCustomerAlgorithm());
            }
        }

        LOG.trace("Initiating multipart upload [{}] from exchange [{}]...", createMultipartUploadRequest, exchange);

        CreateMultipartUploadResponse initResponse
                = getEndpoint().getS3Client().createMultipartUpload(createMultipartUploadRequest.build());
        final long contentLength = Long.valueOf(objectMetadata.get("Content-Length"));
        List<CompletedPart> completedParts = new ArrayList<CompletedPart>();
        long partSize = getConfiguration().getPartSize();
        CompleteMultipartUploadResponse uploadResult = null;

        long filePosition = 0;

        try {
            for (int part = 1; filePosition < contentLength; part++) {
                partSize = Math.min(partSize, contentLength - filePosition);

                UploadPartRequest uploadRequest = UploadPartRequest.builder().bucket(getConfiguration().getBucketName())
                        .key(keyName).uploadId(initResponse.uploadId())
                        .partNumber(part).build();

                LOG.trace("Uploading part [{}] for {}", part, keyName);
                try (InputStream fileInputStream = new FileInputStream(filePayload)) {
                    fileInputStream.skip(filePosition);

                    String etag = getEndpoint().getS3Client()
                            .uploadPart(uploadRequest, RequestBody.fromInputStream(fileInputStream, partSize)).eTag();
                    CompletedPart partUpload = CompletedPart.builder().partNumber(part).eTag(etag).build();
                    completedParts.add(partUpload);
                    filePosition += partSize;
                }
            }
            CompletedMultipartUpload completeMultipartUpload = CompletedMultipartUpload.builder().parts(completedParts).build();
            CompleteMultipartUploadRequest compRequest
                    = CompleteMultipartUploadRequest.builder().multipartUpload(completeMultipartUpload)
                            .bucket(getConfiguration().getBucketName()).key(keyName).uploadId(initResponse.uploadId()).build();

            uploadResult = getEndpoint().getS3Client().completeMultipartUpload(compRequest);

        } catch (Exception e) {
            getEndpoint().getS3Client()
                    .abortMultipartUpload(AbortMultipartUploadRequest.builder().bucket(getConfiguration().getBucketName())
                            .key(keyName).uploadId(initResponse.uploadId()).build());
            throw e;
        }

        Message message = getMessageForResponse(exchange);
        message.setHeader(AWS2S3Constants.E_TAG, uploadResult.eTag());
        if (uploadResult.versionId() != null) {
            message.setHeader(AWS2S3Constants.VERSION_ID, uploadResult.versionId());
        }

        if (getConfiguration().isDeleteAfterWrite()) {
            FileUtil.deleteFile(filePayload);
        }
    }

};