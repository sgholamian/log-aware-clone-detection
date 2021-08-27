//,temp,UnsharableCodecsConflictsTest.java,56,83,temp,UnsharableCodecsConflicts2Test.java,49,77
//,3
public class xxx {
    @Test
    public void canSupplyMultipleCodecsToEndpointPipeline() throws Exception {
        byte[] sPort1 = new byte[8192];
        byte[] sPort2 = new byte[16383];
        Arrays.fill(sPort1, (byte) 0x38);
        Arrays.fill(sPort2, (byte) 0x39);
        byte[] bodyPort1 = (new String(LENGTH_HEADER) + new String(sPort1)).getBytes();
        byte[] bodyPort2 = (new String(LENGTH_HEADER) + new String(sPort2)).getBytes();

        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedBodiesReceived(new String(sPort2) + "9");

        Socket server1 = getSocket("localhost", port.getPort());
        Socket server2 = getSocket("localhost", port2.getPort());

        try {
            sendSopBuffer(bodyPort2, server2);
            sendSopBuffer(bodyPort1, server1);
            sendSopBuffer(new String("9").getBytes(), server2);
        } catch (Exception e) {
            LOG.error("", e);
        } finally {
            server1.close();
            server2.close();
        }

        mock.assertIsSatisfied();
    }

};