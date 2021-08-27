//,temp,AS2ClientManagerIT.java,392,496,temp,AS2ClientManagerIT.java,221,300
//,3
public class xxx {
    @Test
    public void plainMessageSendTest() throws Exception {
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is String
        headers.put("CamelAS2.requestUri", REQUEST_URI);
        // parameter type is String
        headers.put("CamelAS2.subject", SUBJECT);
        // parameter type is String
        headers.put("CamelAS2.from", FROM);
        // parameter type is String
        headers.put("CamelAS2.as2From", AS2_NAME);
        // parameter type is String
        headers.put("CamelAS2.as2To", AS2_NAME);
        // parameter type is org.apache.camel.component.as2.api.AS2MessageStructure
        headers.put("CamelAS2.as2MessageStructure", AS2MessageStructure.PLAIN);
        // parameter type is org.apache.http.entity.ContentType
        headers.put("CamelAS2.ediMessageContentType",
                ContentType.create(AS2MediaType.APPLICATION_EDIFACT, AS2Charset.US_ASCII));
        // parameter type is String
        headers.put("CamelAS2.ediMessageTransferEncoding", EDI_MESSAGE_CONTENT_TRANSFER_ENCODING);
        // parameter type is String
        headers.put("CamelAS2.dispositionNotificationTo", "mrAS2@example.com");

        final Triple<HttpEntity, HttpRequest, HttpResponse> result = executeRequest(headers);
        HttpEntity responseEntity = result.getLeft();
        HttpRequest request = result.getMiddle();
        HttpResponse response = result.getRight();

        assertNotNull(result, "send result");
        LOG.debug("send: " + result);
        assertNotNull(request, "Request");
        assertTrue(request instanceof HttpEntityEnclosingRequest, "Request does not contain body");
        HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
        assertNotNull(entity, "Request body");
        assertTrue(entity instanceof ApplicationEDIEntity, "Request body does not contain EDI entity");
        String ediMessage = ((ApplicationEDIEntity) entity).getEdiMessage();
        assertEquals(EDI_MESSAGE.replaceAll("[\n\r]", ""), ediMessage.replaceAll("[\n\r]", ""), "EDI message is different");

        assertNotNull(response, "Response");
        assertTrue(HttpMessageUtils.getHeaderValue(response, AS2Header.CONTENT_TYPE).startsWith(AS2MimeType.MULTIPART_REPORT),
                "Unexpected response type");
        assertEquals(AS2Constants.MIME_VERSION, HttpMessageUtils.getHeaderValue(response, AS2Header.MIME_VERSION),
                "Unexpected mime version");
        assertEquals(EXPECTED_AS2_VERSION, HttpMessageUtils.getHeaderValue(response, AS2Header.AS2_VERSION),
                "Unexpected AS2 version");
        assertEquals(EXPECTED_MDN_SUBJECT, HttpMessageUtils.getHeaderValue(response, AS2Header.SUBJECT),
                "Unexpected MDN subject");
        assertEquals(MDN_FROM, HttpMessageUtils.getHeaderValue(response, AS2Header.FROM), "Unexpected MDN from");
        assertEquals(AS2_NAME, HttpMessageUtils.getHeaderValue(response, AS2Header.AS2_FROM), "Unexpected AS2 from");
        assertEquals(AS2_NAME, HttpMessageUtils.getHeaderValue(response, AS2Header.AS2_TO), "Unexpected AS2 to");
        assertNotNull(HttpMessageUtils.getHeaderValue(response, AS2Header.MESSAGE_ID), "Missing message id");

        assertNotNull(responseEntity, "Response entity");
        assertTrue(responseEntity instanceof DispositionNotificationMultipartReportEntity, "Unexpected response entity type");
        DispositionNotificationMultipartReportEntity reportEntity
                = (DispositionNotificationMultipartReportEntity) responseEntity;
        assertEquals(2, reportEntity.getPartCount(), "Unexpected number of body parts in report");
        MimeEntity firstPart = reportEntity.getPart(0);
        assertEquals(ContentType.create(AS2MimeType.TEXT_PLAIN, AS2Charset.US_ASCII).toString(),
                firstPart.getContentTypeValue(), "Unexpected content type in first body part of report");
        MimeEntity secondPart = reportEntity.getPart(1);
        assertEquals(ContentType.create(AS2MimeType.MESSAGE_DISPOSITION_NOTIFICATION, AS2Charset.US_ASCII).toString(),
                secondPart.getContentTypeValue(),
                "Unexpected content type in second body part of report");

        assertTrue(secondPart instanceof AS2MessageDispositionNotificationEntity, "");
        AS2MessageDispositionNotificationEntity messageDispositionNotificationEntity
                = (AS2MessageDispositionNotificationEntity) secondPart;
        assertEquals(ORIGIN_SERVER_NAME, messageDispositionNotificationEntity.getReportingUA(),
                "Unexpected value for reporting UA");
        assertEquals(AS2_NAME, messageDispositionNotificationEntity.getFinalRecipient(),
                "Unexpected value for final recipient");
        assertEquals(HttpMessageUtils.getHeaderValue(request, AS2Header.MESSAGE_ID),
                messageDispositionNotificationEntity.getOriginalMessageId(), "Unexpected value for original message ID");
        assertEquals(DispositionMode.AUTOMATIC_ACTION_MDN_SENT_AUTOMATICALLY,
                messageDispositionNotificationEntity.getDispositionMode(), "Unexpected value for disposition mode");
        assertEquals(AS2DispositionType.PROCESSED, messageDispositionNotificationEntity.getDispositionType(),
                "Unexpected value for disposition type");

    }

};