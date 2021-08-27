//,temp,MimeMultipartAlternativeWithLongerFilenameTest.java,69,88,temp,MimeMultipartAlternativeWithContentTypeTest.java,64,80
//,3
public class xxx {
    private void verifyTheRecivedEmail(String expectString) throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.assertIsSatisfied();

        Exchange out = mock.assertExchangeReceived(0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(((MailMessage) out.getIn()).getMessage().getSize());
        ((MailMessage) out.getIn()).getMessage().writeTo(baos);
        String dumpedMessage = baos.toString();
        assertTrue(dumpedMessage.indexOf(expectString) > 0, "There should have the " + expectString);
        log.trace("multipart alternative: \n{}", dumpedMessage);

        // plain text
        assertEquals(alternativeBody, out.getIn().getBody(String.class));

        // attachment
        Map<String, DataHandler> attachments = out.getIn(AttachmentMessage.class).getAttachments();
        assertNotNull(attachments, "Should not have null attachments");
        assertEquals(1, attachments.size());
        assertEquals(2, out.getIn().getBody(MimeMultipart.class).getCount(), "multipart body should have 2 parts");
    }

};