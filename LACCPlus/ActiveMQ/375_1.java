//,temp,MQTTCodecTest.java,196,227,temp,MQTTCodecTest.java,133,161
//,3
public class xxx {
    @Test
    public void testProcessInBytes() throws Exception {

        CONNECT connect = new CONNECT();
        connect.cleanSession(false);
        connect.clientId(new UTF8Buffer("test"));
        connect.userName(new UTF8Buffer("user"));
        connect.password(new UTF8Buffer("pass"));

        DataByteArrayOutputStream output = new DataByteArrayOutputStream();
        wireFormat.marshal(connect.encode(), output);
        Buffer marshalled = output.toBuffer();

        DataByteArrayInputStream input = new DataByteArrayInputStream(marshalled);

        int size = marshalled.length();

        for (int i = 0; i < size; ++i) {
            codec.parse(input, 1);
        }

        assertTrue(!frames.isEmpty());
        assertEquals(1, frames.size());

        connect = new CONNECT().decode(frames.get(0));
        LOG.info("Unmarshalled: {}", connect);
        assertFalse(connect.cleanSession());

        assertEquals("user", connect.userName().toString());
        assertEquals("pass", connect.password().toString());
        assertEquals("test", connect.clientId().toString());
    }

};