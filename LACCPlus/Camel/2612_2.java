//,temp,AS2MessageTest.java,498,565,temp,AS2MessageTest.java,440,491
//,3
public class xxx {
    public void envelopedMessageTest(AS2EncryptionAlgorithm encryptionAlgorithm) throws Exception {
        AS2ClientConnection clientConnection = new AS2ClientConnection(
                AS2_VERSION, USER_AGENT, CLIENT_FQDN,
                TARGET_HOST, TARGET_PORT);
        AS2ClientManager clientManager = new AS2ClientManager(clientConnection);

        LOG.info("Key Algoritm: " + signingKP.getPrivate().getAlgorithm());

        HttpCoreContext httpContext = clientManager.send(EDI_MESSAGE, REQUEST_URI, SUBJECT, FROM, AS2_NAME, AS2_NAME,
                AS2MessageStructure.ENCRYPTED,
                ContentType.create(AS2MediaType.APPLICATION_EDIFACT, AS2Charset.US_ASCII), null,
                AS2SignatureAlgorithm.SHA256WITHRSA, certList.toArray(new Certificate[0]), signingKP.getPrivate(), null,
                DISPOSITION_NOTIFICATION_TO, SIGNED_RECEIPT_MIC_ALGORITHMS, encryptionAlgorithm,
                certList.toArray(new Certificate[0]));

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
        assertTrue(entity instanceof ApplicationPkcs7MimeEnvelopedDataEntity, "Unexpected request entity type");
        ApplicationPkcs7MimeEnvelopedDataEntity envelopedEntity = (ApplicationPkcs7MimeEnvelopedDataEntity) entity;
        assertTrue(envelopedEntity.isMainBody(), "Entity not set as main body of request");

        // Validated enveloped part.
        MimeEntity encryptedEntity = envelopedEntity.getEncryptedEntity(signingKP.getPrivate());
        assertTrue(encryptedEntity instanceof ApplicationEDIFACTEntity, "Enveloped mime part incorrect type ");
        ApplicationEDIFACTEntity ediEntity = (ApplicationEDIFACTEntity) encryptedEntity;
        assertTrue(ediEntity.getContentType().getValue().startsWith(AS2MediaType.APPLICATION_EDIFACT),
                "Unexpected content type for enveloped mime part");
        assertFalse(ediEntity.isMainBody(), "Enveloped mime type set as main body of request");
        assertEquals(EDI_MESSAGE.replaceAll("[\n\r]", ""), ediEntity.getEdiMessage().replaceAll("[\n\r]", ""),
                "Unexpected content for enveloped mime part");
    }

};