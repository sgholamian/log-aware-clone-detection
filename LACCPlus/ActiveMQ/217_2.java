//,temp,MulticastTransportTest.java,40,52,temp,UdpTransportTest.java,52,58
//,3
public class xxx {
    protected Transport createConsumer() throws Exception {
        LOG.info("Consumer on port: " + consumerPort);
        OpenWireFormat wireFormat = createWireFormat();
        UdpTransport transport = new UdpTransport(wireFormat, consumerPort);
        transport.setSequenceGenerator(new IntSequenceGenerator());
        return new CommandJoiner(transport, wireFormat);
    }

};