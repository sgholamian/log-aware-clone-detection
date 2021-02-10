//,temp,Configuration.java,2371,2386,temp,Configuration.java,2347,2362
//,3
public class xxx {
  public Reader getConfResourceAsReader(String name) {
    try {
      URL url= getResource(name);

      if (url == null) {
        LOG.info(name + " not found");
        return null;
      } else {
        LOG.info("found resource " + name + " at " + url);
      }

      return new InputStreamReader(url.openStream(), Charsets.UTF_8);
    } catch (Exception e) {
      return null;
    }
  }

};