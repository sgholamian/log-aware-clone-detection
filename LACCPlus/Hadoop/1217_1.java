//,temp,TimelineClientImpl.java,505,513,temp,URLConnectionFactory.java,79,90
//,3
public class xxx {
  private static ConnectionConfigurator newConnConfigurator(Configuration conf) {
    try {
      return newSslConnConfigurator(DEFAULT_SOCKET_TIMEOUT, conf);
    } catch (Exception e) {
      LOG.debug("Cannot load customized ssl related configuration. " +
          "Fallback to system-generic settings.", e);
      return DEFAULT_TIMEOUT_CONN_CONFIGURATOR;
    }
  }

};