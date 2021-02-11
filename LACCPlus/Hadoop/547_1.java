//,temp,TestPathFilter.java,85,103,temp,TestServletFilter.java,84,101
//,2
public class xxx {
  static void access(String urlstring) throws IOException {
    LOG.warn("access " + urlstring);
    URL url = new URL(urlstring);
    
    URLConnection connection = url.openConnection();
    connection.connect();
    
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(
          connection.getInputStream()));
      try {
        for(; in.readLine() != null; );
      } finally {
        in.close();
      }
    } catch(IOException ioe) {
      LOG.warn("urlstring=" + urlstring, ioe);
    }
  }

};