//,temp,sample_1929.java,2,7,temp,sample_719.java,2,6
//,3
public class xxx {
private static TServer getTThreadedSelectorServer(TProtocolFactory protocolFactory, TProcessor processor, TTransportFactory transportFactory, int workerThreads, int selectorThreads, int maxCallQueueSize, InetSocketAddress inetSocketAddress, ThriftMetrics metrics) throws TTransportException {
TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(inetSocketAddress);


log.info("starting hbase threadedselector thrift server on");
}

};