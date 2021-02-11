//,temp,HttpServer2.java,853,890,temp,HttpServer.java,724,759
//,3
public class xxx {
  public void start() throws IOException {
    try {
      try {
        openListener();
        LOG.info("Jetty bound to port " + listener.getLocalPort());
        webServer.start();
      } catch (IOException ex) {
        LOG.info("HttpServer.start() threw a non Bind IOException", ex);
        throw ex;
      } catch (MultiException ex) {
        LOG.info("HttpServer.start() threw a MultiException", ex);
        throw ex;
      }
      // Make sure there is no handler failures.
      Handler[] handlers = webServer.getHandlers();
      for (int i = 0; i < handlers.length; i++) {
        if (handlers[i].isFailed()) {
          throw new IOException(
              "Problem in starting http server. Server handlers failed");
        }
      }
      // Make sure there are no errors initializing the context.
      Throwable unavailableException = webAppContext.getUnavailableException();
      if (unavailableException != null) {
        // Have to stop the webserver, or else its non-daemon threads
        // will hang forever.
        webServer.stop();
        throw new IOException("Unable to initialize WebAppContext",
            unavailableException);
      }
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new IOException("Problem starting http server", e);
    }
  }

};