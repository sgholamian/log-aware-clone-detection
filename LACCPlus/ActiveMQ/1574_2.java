//,temp,UnreliableUdpTransportTest.java,64,76,temp,UnreliableUdpTransportTest.java,49,62
//,3
public class xxx {
    @Override
    protected Transport createProducer() throws Exception {
        LOG.info("Producer using URI: " + producerURI);

        OpenWireFormat wireFormat = createWireFormat();
        UnreliableUdpTransport transport = new UnreliableUdpTransport(wireFormat, new URI(producerURI));
        transport.setDropCommandStrategy(dropStrategy);

        ReliableTransport reliableTransport = new ReliableTransport(transport, transport);
        Replayer replayer = reliableTransport.getReplayer();
        reliableTransport.setReplayStrategy(createReplayStrategy(replayer));

        return new CommandJoiner(reliableTransport, wireFormat);
    }

};