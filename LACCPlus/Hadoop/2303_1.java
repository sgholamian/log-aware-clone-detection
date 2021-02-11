//,temp,RPC.java,665,694,temp,RpcClientFactoryPBImpl.java,89,109
//,3
public class xxx {
  public static void stopProxy(Object proxy) {
    if (proxy == null) {
      throw new HadoopIllegalArgumentException(
          "Cannot close proxy since it is null");
    }
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
    } catch (IOException e) {
      LOG.error("Closing proxy or invocation handler caused exception", e);
    } catch (IllegalArgumentException e) {
      LOG.error("RPC.stopProxy called on non proxy: class=" + proxy.getClass().getName(), e);
    }
    
    // If you see this error on a mock object in a unit test you're
    // developing, make sure to use MockitoUtil.mockProtocol() to
    // create your mock.
    throw new HadoopIllegalArgumentException(
        "Cannot close proxy - is not Closeable or "
            + "does not provide closeable invocation handler "
            + proxy.getClass());
  }

};