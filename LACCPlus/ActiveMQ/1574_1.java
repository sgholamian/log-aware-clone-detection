//,temp,UnreliableUdpTransportTest.java,64,76,temp,UnreliableUdpTransportTest.java,49,62
//,3
public class xxx {
    @Override
    protected Transport createConsumer() throws Exception {
        LOG.info("Consumer on port: " + consumerPort);
        OpenWireFormat wireFormat = createWireFormat();
        UdpTransport transport = new UdpTransport(wireFormat, consumerPort);

        ReliableTransport reliableTransport = new ReliableTransport(transport, transport);
        Replayer replayer = reliableTransport.getReplayer();
        reliableTransport.setReplayStrategy(createReplayStrategy(replayer));

        ResponseRedirectInterceptor redirectInterceptor = new ResponseRedirectInterceptor(reliableTransport, transport);
        return new CommandJoiner(redirectInterceptor, wireFormat);
    }

};