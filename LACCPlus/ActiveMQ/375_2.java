//,temp,MQTTCodecTest.java,196,227,temp,MQTTCodecTest.java,133,161
//,3
public class xxx {
    @Test
    public void testConnectWithCredentialsBackToBack() throws Exception {

        CONNECT connect = new CONNECT();
        connect.cleanSession(false);
        connect.clientId(new UTF8Buffer("test"));
        connect.userName(new UTF8Buffer("user"));
        connect.password(new UTF8Buffer("pass"));

        DataByteArrayOutputStream output = new DataByteArrayOutputStream();
        wireFormat.marshal(connect.encode(), output);
        wireFormat.marshal(connect.encode(), output);
        Buffer marshalled = output.toBuffer();

        DataByteArrayInputStream input = new DataByteArrayInputStream(marshalled);
        codec.parse(input, marshalled.length());

        assertTrue(!frames.isEmpty());
        assertEquals(2, frames.size());

        for (MQTTFrame frame : frames) {
            connect = new CONNECT().decode(frame);
            LOG.info("Unmarshalled: {}", connect);
            assertFalse(connect.cleanSession());
            assertEquals("user", connect.userName().toString());
            assertEquals("pass", connect.password().toString());
            assertEquals("test", connect.clientId().toString());
        }
    }

};