//,temp,HttpSendCompressedMessagesTest.java,132,165,temp,HttpSendCompressedMessagesTest.java,85,118
//,2
public class xxx {
    private void doTestTextMessageCompression() throws Exception {
        ActiveMQTextMessage tcpMessage = (ActiveMQTextMessage) tcpConsumer.receive(TimeUnit.SECONDS.toMillis(3));
        ActiveMQTextMessage httpMessage = (ActiveMQTextMessage) httpConsumer.receive(TimeUnit.SECONDS.toMillis(3));

        assertNotNull(tcpMessage);
        assertNotNull(httpMessage);

        ByteSequence tcpContent = tcpMessage.getContent();
        ByteSequence httpContent = httpMessage.getContent();

        assertNotNull(tcpContent);
        assertNotNull(httpContent);

        assertTrue(tcpMessage.isCompressed());
        assertTrue(httpMessage.isCompressed());

        int tcpCompressedSize = tcpContent.getLength();
        int httpCompressedSize = httpContent.getLength();

        assertEquals(tcpContent.getLength(), httpContent.getLength());
        assertEquals(tcpMessage.getText(), httpMessage.getText());

        LOG.info("Received Message on TCP: " + tcpMessage.toString());
        LOG.info("Received Message on HTTP: " + httpMessage.toString());

        sendTextMessage(false);

        ActiveMQTextMessage uncompressedHttpMessage = (ActiveMQTextMessage)
            httpConsumer.receive(TimeUnit.SECONDS.toMillis(3));
        int httpUncompressedSize = uncompressedHttpMessage.getContent().getLength();

        assertTrue(httpUncompressedSize > httpCompressedSize);
        assertTrue(httpUncompressedSize > tcpCompressedSize);
    }

};