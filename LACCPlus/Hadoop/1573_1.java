//,temp,HttpServer2.java,853,890,temp,HttpServer.java,724,759
//,3
public class xxx {
  public void start() throws IOException {
    try {
      try {
        openListeners();
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
      for (Handler handler : handlers) {
        if (handler.isFailed()) {
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
    } catch (InterruptedException e) {
      throw (IOException) new InterruptedIOException(
          "Interrupted while starting HTTP server").initCause(e);
    } catch (Exception e) {
      throw new IOException("Problem starting http server", e);
    }
  }

};