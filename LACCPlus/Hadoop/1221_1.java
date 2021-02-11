//,temp,DBCountPageView.java,111,128,temp,TestDataDrivenDBInputFormat.java,85,104
//,3
public class xxx {
  private void shutdown() {
    try {
      connection.commit();
      connection.close();
    }catch (Throwable ex) {
      LOG.warn("Exception occurred while closing connection :"
          + StringUtils.stringifyException(ex));
    } finally {
      try {
        if(server != null) {
          server.shutdown();
        }
      }catch (Throwable ex) {
        LOG.warn("Exception occurred while shutting down HSQLDB :"
            + StringUtils.stringifyException(ex));
      }
    }
  }

};