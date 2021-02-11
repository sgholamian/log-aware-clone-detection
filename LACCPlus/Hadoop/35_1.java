//,temp,ClientServiceDelegate.java,294,304,temp,RMProxy.java,119,126
//,3
public class xxx {
  MRClientProtocol instantiateAMProxy(final InetSocketAddress serviceAddr)
      throws IOException {
    LOG.trace("Connecting to ApplicationMaster at: " + serviceAddr);
    YarnRPC rpc = YarnRPC.create(conf);
    MRClientProtocol proxy = 
         (MRClientProtocol) rpc.getProxy(MRClientProtocol.class,
            serviceAddr, conf);
    usingAMProxy.set(true);
    LOG.trace("Connected to ApplicationMaster at: " + serviceAddr);
    return proxy;
  }

};