//,temp,RPC.java,655,684,temp,RpcClientFactoryPBImpl.java,90,110
//,3
public class xxx {
  @Override
  public void stopClient(Object proxy) {
    try {
      if (proxy instanceof Closeable) {
        ((Closeable) proxy).close();
        return;
      } else {
        InvocationHandler handler = Proxy.getInvocationHandler(proxy);
        if (handler instanceof Closeable) {
          ((Closeable) handler).close();
          return;
        }
      }
    } catch (Exception e) {
      LOG.error("Cannot call close method due to Exception. " + "Ignoring.", e);
      throw new YarnRuntimeException(e);
    }
    throw new HadoopIllegalArgumentException(
      "Cannot close proxy - is not Closeable or "
          + "does not provide closeable invocation handler " + proxy.getClass());
  }

};