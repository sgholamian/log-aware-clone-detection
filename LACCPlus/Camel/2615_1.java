//,temp,AS2MessageTest.java,762,829,temp,AS2ServerManagerIT.java,211,288
//,3
public class xxx {
    @Test
    public void compressedAndSignedMessageTest() throws Exception {
        AS2ClientConnection clientConnection = new AS2ClientConnection(
                AS2_VERSION, USER_AGENT, CLIENT_FQDN,
                TARGET_HOST, TARGET_PORT);
        AS2ClientManager clientManager = new AS2ClientManager(clientConnection);

        LOG.info("Key Algoritm: " + signingKP.getPrivate().getAlgorithm());

        HttpCoreContext httpContext = clientManager.send(EDI_MESSAGE, REQUEST_URI, SUBJECT, FROM, AS2_NAME, AS2_NAME,
                AS2MessageStructure.SIGNED_COMPRESSED,
                ContentType.create(AS2MediaType.APPLICATION_EDIFACT, AS2Charset.US_ASCII), "base64",
                AS2SignatureAlgorithm.SHA256WITHRSA, certList.toArray(new Certificate[0]), signingKP.getPrivate(),
                AS2CompressionAlgorithm.ZLIB,
                DISPOSITION_NOTIFICATION_TO, SIGNED_RECEIPT_MIC_ALGORITHMS, null,
                null);

        HttpRequest request = httpContext.getRequest();
        assertEquals(METHOD, request.getRequestLine().getMethod(), "Unexpected method value");
        assertEquals(REQUEST_URI, request.getRequestLine().getUri(), "Unexpected request URI value");
        assertEquals(HttpVersion.HTTP_1_1, request.getRequestLine().getProtocolVersion(), "Unexpected HTTP version value");

        assertEquals(SUBJECT, request.getFirstHeader(AS2Header.SUBJECT).getValue(), "Unexpected subject value");
        assertEquals(FROM, request.getFirstHeader(AS2Header.FROM).getValue(), "Unexpected from value");
        assertEquals(AS2_VERSION, request.getFirstHeader(AS2Header.AS2_VERSION).getValue(), "Unexpected AS2 version value");
        assertEquals(AS2_NAME, request.getFirstHeader(AS2Header.AS2_FROM).getValue(), "Unexpected AS2 from value");
        assertEquals(AS2_NAME, request.getFirstHeader(AS2Header.AS2_TO).getValue(), "Unexpected AS2 to value");
        assertTrue(request.getFirstHeader(AS2Header.MESSAGE_ID).getValue().endsWith(CLIENT_FQDN + ">"),
                "Unexpected message id value");
        assertEquals(TARGET_HOST + ":" + TARGET_PORT, request.getFirstHeader(AS2Header.TARGET_HOST).getValue(),
                "Unexpected target host value");
        assertEquals(USER_AGENT, request.getFirstHeader(AS2Header.USER_AGENT).getValue(), "Unexpected user agent value");
        assertNotNull(request.getFirstHeader(AS2Header.DATE), "Date value missing");
        assertNotNull(request.getFirstHeader(AS2Header.CONTENT_LENGTH), "Content length value missing");
        assertTrue(request.getFirstHeader(AS2Header.CONTENT_TYPE).getValue().startsWith(AS2MimeType.APPLICATION_PKCS7_MIME),
                "Unexpected content type for message");

        assertTrue(request instanceof BasicHttpEntityEnclosingRequest, "Request does not contain entity");
        HttpEntity entity = ((BasicHttpEntityEnclosingRequest) request).getEntity();
        assertNotNull(entity, "Request does not contain entity");
        assertTrue(entity instanceof ApplicationPkcs7MimeCompressedDataEntity, "Unexpected request entity type");
        ApplicationPkcs7MimeCompressedDataEntity compressedDataEntity = (ApplicationPkcs7MimeCompressedDataEntity) entity;
        assertTrue(compressedDataEntity.isMainBody(), "Entity not set as main body of request");

        // Validated compressed part.
        MimeEntity compressedEntity = compressedDataEntity.getCompressedEntity(new ZlibExpanderProvider());
        assertTrue(compressedEntity instanceof MultipartSignedEntity, "Enveloped mime part incorrect type ");
        MultipartSignedEntity multipartSignedEntity = (MultipartSignedEntity) compressedEntity;
        assertTrue(multipartSignedEntity.getContentType().getValue().startsWith(AS2MediaType.MULTIPART_SIGNED),
                "Unexpected content type for compressed entity");
        assertFalse(multipartSignedEntity.isMainBody(), "Multipart signed entity set as main body of request");
        assertEquals(2, multipartSignedEntity.getPartCount(), "Multipart signed entity contains invalid number of mime parts");

        // Validated first mime part.
        assertTrue(multipartSignedEntity.getPart(0) instanceof ApplicationEDIFACTEntity, "First mime part incorrect type ");
        ApplicationEDIFACTEntity ediEntity = (ApplicationEDIFACTEntity) multipartSignedEntity.getPart(0);
        assertTrue(ediEntity.getContentType().getValue().startsWith(AS2MediaType.APPLICATION_EDIFACT),
                "Unexpected content type for first mime part");
        assertFalse(ediEntity.isMainBody(), "First mime type set as main body of request");

        // Validate second mime part.
        assertTrue(multipartSignedEntity.getPart(1) instanceof ApplicationPkcs7SignatureEntity,
                "Second mime part incorrect type ");
        ApplicationPkcs7SignatureEntity signatureEntity = (ApplicationPkcs7SignatureEntity) multipartSignedEntity.getPart(1);
        assertTrue(signatureEntity.getContentType().getValue().startsWith(AS2MediaType.APPLICATION_PKCS7_SIGNATURE),
                "Unexpected content type for second mime part");
        assertFalse(signatureEntity.isMainBody(), "First mime type set as main body of request");
    }

};