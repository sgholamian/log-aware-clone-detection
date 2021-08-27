//,temp,HttpSendCompressedMessagesTest.java,132,165,temp,HttpSendCompressedMessagesTest.java,85,118
//,2
public class xxx {
    private void doTestBytesMessageCompression() throws Exception {
        ActiveMQBytesMessage tcpMessage = (ActiveMQBytesMessage) tcpConsumer.receive(TimeUnit.SECONDS.toMillis(3));
        ActiveMQBytesMessage httpMessage = (ActiveMQBytesMessage) httpConsumer.receive(TimeUnit.SECONDS.toMillis(3));

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
        assertEquals(tcpMessage.readUTF(), httpMessage.readUTF());

        LOG.info("Received Message on TCP: " + tcpMessage.toString());
        LOG.info("Received Message on HTTP: " + httpMessage.toString());

        sendBytesMessage(false);

        ActiveMQBytesMessage uncompressedHttpMessage = (ActiveMQBytesMessage)
            httpConsumer.receive(TimeUnit.SECONDS.toMillis(3));
        int httpUncompressedSize = uncompressedHttpMessage.getContent().getLength();

        assertTrue(httpUncompressedSize > httpCompressedSize);
        assertTrue(httpUncompressedSize > tcpCompressedSize);
    }

};