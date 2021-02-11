//,temp,TestPathFilter.java,105,150,temp,TestGlobalFilter.java,104,146
//,3
public class xxx {
  @Test
  public void testPathSpecFilters() throws Exception {
    Configuration conf = new Configuration();
    
    //start a http server with CountingFilter
    conf.set(HttpServer2.FILTER_INITIALIZER_PROPERTY,
        RecordingFilter.Initializer.class.getName());
    String[] pathSpecs = { "/path", "/path/*" };
    HttpServer2 http = createTestServer(conf, pathSpecs);
    http.start();

    final String baseURL = "/path";
    final String baseSlashURL = "/path/";
    final String addedURL = "/path/nodes";
    final String addedSlashURL = "/path/nodes/";
    final String longURL = "/path/nodes/foo/job";
    final String rootURL = "/";
    final String allURL = "/*";

    final String[] filteredUrls = {baseURL, baseSlashURL, addedURL, 
        addedSlashURL, longURL};
    final String[] notFilteredUrls = {rootURL, allURL};

    // access the urls and verify our paths specs got added to the 
    // filters
    final String prefix = "http://"
        + NetUtils.getHostPortString(http.getConnectorAddress(0));
    try {
      for(int i = 0; i < filteredUrls.length; i++) {
        access(prefix + filteredUrls[i]);
      }
      for(int i = 0; i < notFilteredUrls.length; i++) {
        access(prefix + notFilteredUrls[i]);
      }
    } finally {
      http.stop();
    }

    LOG.info("RECORDS = " + RECORDS);
    
    //verify records
    for(int i = 0; i < filteredUrls.length; i++) {
      assertTrue(RECORDS.remove(filteredUrls[i]));
    }
    assertTrue(RECORDS.isEmpty());
  }

};