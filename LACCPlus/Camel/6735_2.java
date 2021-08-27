//,temp,UnsharableCodecsConflictsTest.java,56,83,temp,UnsharableCodecsConflicts2Test.java,49,77
//,3
public class xxx {
    @Test
    public void unsharableCodecsConflictsTest() throws Exception {
        byte[] data1 = new byte[8192];
        byte[] data2 = new byte[16383];
        Arrays.fill(data1, (byte) 0x38);
        Arrays.fill(data2, (byte) 0x39);
        byte[] body1 = (new String(LENGTH_HEADER) + new String(data1)).getBytes();
        byte[] body2 = (new String(LENGTH_HEADER) + new String(data2)).getBytes();

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived(new String(data2) + "9");

        Socket client1 = getSocket("localhost", port);
        Socket client2 = getSocket("localhost", port);

        // use two clients to send to the same server at the same time
        try {
            sendBuffer(body2, client2);
            sendBuffer(body1, client1);
            sendBuffer(new String("9").getBytes(), client2);
        } catch (Exception e) {
            LOG.error("", e);
        } finally {
            client1.close();
            client2.close();
        }

        mock.assertIsSatisfied();
    }

};