//,temp,ThriftProducer.java,155,162,temp,ThriftProducer.java,145,153
//,3
public class xxx {
    protected void initializeAsyncTransport() throws IOException, TTransportException {
        if (!ObjectHelper.isEmpty(configuration.getHost()) && !ObjectHelper.isEmpty(configuration.getPort())) {
            LOG.info("Creating transport to the remote Thrift server {}:{}", configuration.getHost(), configuration.getPort());
            asyncTransport = new TNonblockingSocket(configuration.getHost(), configuration.getPort());
        } else {
            throw new IllegalArgumentException("No connection properties (host, port) specified");
        }
    }

};