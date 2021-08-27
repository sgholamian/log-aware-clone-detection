//,temp,AS2MessageTest.java,893,968,temp,AS2ServerManagerIT.java,149,209
//,3
public class xxx {
    @Test
    public void receivePlainEDIMessageTest() throws Exception {
        AS2ClientConnection clientConnection
                = new AS2ClientConnection(AS2_VERSION, USER_AGENT, CLIENT_FQDN, TARGET_HOST, TARGET_PORT);
        AS2ClientManager clientManager = new AS2ClientManager(clientConnection);

        clientManager.send(EDI_MESSAGE, REQUEST_URI, SUBJECT, FROM, AS2_NAME, AS2_NAME, AS2MessageStructure.PLAIN,
                ContentType.create(AS2MediaType.APPLICATION_EDIFACT, AS2Charset.US_ASCII), null, null, null, null,
                null, DISPOSITION_NOTIFICATION_TO, SIGNED_RECEIPT_MIC_ALGORITHMS, null, null);

        MockEndpoint mockEndpoint = getMockEndpoint("mock:as2RcvMsgs");
        mockEndpoint.expectedMinimumMessageCount(1);
        mockEndpoint.setResultWaitTime(TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS));
        mockEndpoint.assertIsSatisfied();

        final List<Exchange> exchanges = mockEndpoint.getExchanges();
        assertNotNull(exchanges, "listen result");
        assertFalse(exchanges.isEmpty(), "listen result");
        LOG.debug("poll result: " + exchanges);

        Exchange exchange = exchanges.get(0);
        Message message = exchange.getIn();
        assertNotNull(message, "exchange message");

        HttpCoreContext coreContext = exchange.getProperty(AS2Constants.AS2_INTERCHANGE, HttpCoreContext.class);
        assertNotNull(coreContext, "context");
        HttpRequest request = coreContext.getRequest();
        assertNotNull(request, "request");
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
        assertTrue(request.getFirstHeader(AS2Header.CONTENT_TYPE).getValue().startsWith(AS2MediaType.APPLICATION_EDIFACT),
                "Unexpected content type for message");

        assertTrue(request instanceof BasicHttpEntityEnclosingRequest, "Request does not contain entity");
        HttpEntity entity = ((BasicHttpEntityEnclosingRequest) request).getEntity();
        assertNotNull(entity, "Request does not contain entity");
        assertTrue(entity instanceof ApplicationEDIFACTEntity, "Unexpected request entity type");
        ApplicationEDIFACTEntity ediEntity = (ApplicationEDIFACTEntity) entity;
        assertTrue(ediEntity.getContentType().getValue().startsWith(AS2MediaType.APPLICATION_EDIFACT),
                "Unexpected content type for entity");
        assertTrue(ediEntity.isMainBody(), "Entity not set as main body of request");
        String rcvdMessage = ediEntity.getEdiMessage().replaceAll("\r", "");
        assertEquals(EDI_MESSAGE, rcvdMessage, "EDI message does not match");

        String rcvdMessageFromBody = message.getBody(String.class);
        assertEquals(EDI_MESSAGE.replaceAll("[\n\r]", ""), rcvdMessageFromBody.replaceAll("[\n\r]", ""),
                "EDI message does not match");
    }

};