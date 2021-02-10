//,temp,TestPathFilter.java,105,150,temp,TestGlobalFilter.java,104,146
//,3
public class xxx {
  @Test
  public void testServletFilter() throws Exception {
    Configuration conf = new Configuration();
    
    //start a http server with CountingFilter
    conf.set(HttpServer2.FILTER_INITIALIZER_PROPERTY,
        RecordingFilter.Initializer.class.getName());
    HttpServer2 http = createTestServer(conf);
    http.start();

    final String fsckURL = "/fsck";
    final String stacksURL = "/stacks";
    final String ajspURL = "/a.jsp";
    final String listPathsURL = "/listPaths";
    final String dataURL = "/data";
    final String streamFile = "/streamFile";
    final String rootURL = "/";
    final String allURL = "/*";
    final String outURL = "/static/a.out";
    final String logURL = "/logs/a.log";

    final String[] urls = {fsckURL, stacksURL, ajspURL, listPathsURL, 
        dataURL, streamFile, rootURL, allURL, outURL, logURL};

    //access the urls
    final String prefix = "http://"
        + NetUtils.getHostPortString(http.getConnectorAddress(0));
    try {
      for(int i = 0; i < urls.length; i++) {
        access(prefix + urls[i]);
      }
    } finally {
      http.stop();
    }

    LOG.info("RECORDS = " + RECORDS);
    
    //verify records
    for(int i = 0; i < urls.length; i++) {
      assertTrue(RECORDS.remove(urls[i]));
    }
    assertTrue(RECORDS.isEmpty());
  }

};