//,temp,ClientServiceDelegate.java,294,304,temp,RMProxy.java,119,126
//,3
public class xxx {
  @Deprecated
  public static <T> T createRMProxy(final Configuration conf,
      final Class<T> protocol, InetSocketAddress rmAddress) throws IOException {
    RetryPolicy retryPolicy = createRetryPolicy(conf);
    T proxy = RMProxy.<T>getProxy(conf, protocol, rmAddress);
    LOG.info("Connecting to ResourceManager at " + rmAddress);
    return (T) RetryProxy.create(protocol, proxy, retryPolicy);
  }

};