//,temp,JavaUtils.java,107,114,temp,ServerUtils.java,73,80
//,2
public class xxx {
  public static String hostname() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      LOG.error("Unable to resolve my host name " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

};