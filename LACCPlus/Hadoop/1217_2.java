//,temp,TimelineClientImpl.java,505,513,temp,URLConnectionFactory.java,79,90
//,3
public class xxx {
  public static URLConnectionFactory newDefaultURLConnectionFactory(Configuration conf) {
    ConnectionConfigurator conn = null;
    try {
      conn = newSslConnConfigurator(DEFAULT_SOCKET_TIMEOUT, conf);
    } catch (Exception e) {
      LOG.debug(
          "Cannot load customized ssl related configuration. Fallback to system-generic settings.",
          e);
      conn = DEFAULT_TIMEOUT_CONN_CONFIGURATOR;
    }
    return new URLConnectionFactory(conn);
  }

};