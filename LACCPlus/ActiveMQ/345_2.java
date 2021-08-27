//,temp,HttpSendCompressedMessagesTest.java,226,259,temp,HttpSendCompressedMessagesTest.java,179,212
//,3
public class xxx {
    private void doTestStreamMessageCompression() throws Exception {
        ActiveMQStreamMessage tcpMessage = (ActiveMQStreamMessage) tcpConsumer.receive(TimeUnit.SECONDS.toMillis(3));
        ActiveMQStreamMessage httpMessage = (ActiveMQStreamMessage) httpConsumer.receive(TimeUnit.SECONDS.toMillis(3));

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
        assertEquals(tcpMessage.readString(), httpMessage.readString());

        LOG.info("Received Message on TCP: " + tcpMessage.toString());
        LOG.info("Received Message on HTTP: " + httpMessage.toString());

        sendStreamMessage(false);

        ActiveMQStreamMessage uncompressedHttpMessage = (ActiveMQStreamMessage)
            httpConsumer.receive(TimeUnit.SECONDS.toMillis(3));
        int httpUncompressedSize = uncompressedHttpMessage.getContent().getLength();

        assertTrue(httpUncompressedSize > httpCompressedSize);
        assertTrue(httpUncompressedSize > tcpCompressedSize);
    }

};