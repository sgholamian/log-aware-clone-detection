//,temp,MulticastTransportTest.java,40,52,temp,UdpTransportTest.java,52,58
//,3
public class xxx {
    protected Transport createProducer() throws Exception {
        LOG.info("Producer using URI: " + multicastURI);
        
        // we are not using the TransportFactory as this assumes that
        // transports talk to a server using a WireFormat Negotiation step
        // rather than talking directly to each other
        
        OpenWireFormat wireFormat = createWireFormat();
        MulticastTransport transport = new MulticastTransport(wireFormat, new URI(multicastURI));
        transport.setLoopBackMode(false);
        transport.setSequenceGenerator(new IntSequenceGenerator());
        return new CommandJoiner(transport, wireFormat);
    }

};