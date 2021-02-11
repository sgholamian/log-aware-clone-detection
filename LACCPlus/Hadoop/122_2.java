//,temp,HttpServer2.java,935,972,temp,HttpServer.java,810,851
//,3
public class xxx {
  public void stop() throws Exception {
    MultiException exception = null;
    try {
      listener.close();
    } catch (Exception e) {
      LOG.error("Error while stopping listener for webapp"
          + webAppContext.getDisplayName(), e);
      exception = addMultiException(exception, e);
    }

    try {
      if (sslFactory != null) {
          sslFactory.destroy();
      }
    } catch (Exception e) {
      LOG.error("Error while destroying the SSLFactory"
          + webAppContext.getDisplayName(), e);
      exception = addMultiException(exception, e);
    }

    try {
      // clear & stop webAppContext attributes to avoid memory leaks.
      webAppContext.clearAttributes();
      webAppContext.stop();
    } catch (Exception e) {
      LOG.error("Error while stopping web app context for webapp "
          + webAppContext.getDisplayName(), e);
      exception = addMultiException(exception, e);
    }
    try {
      webServer.stop();
    } catch (Exception e) {
      LOG.error("Error while stopping web server for webapp "
          + webAppContext.getDisplayName(), e);
      exception = addMultiException(exception, e);
    }

    if (exception != null) {
      exception.ifExceptionThrow();
    }

  }

};