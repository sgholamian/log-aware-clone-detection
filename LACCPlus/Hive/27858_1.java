//,temp,BeelineSiteParser.java,77,102,temp,UserHS2ConnectionFileParser.java,70,96
//,3
public class xxx {
  @Override
  public Properties getConnectionProperties() throws BeelineSiteParseException {
    Properties props = new Properties();
    String fileLocation = getFileLocation();
    if (fileLocation == null) {
      log.debug("Could not find Beeline configuration file: {}", DEFAULT_BEELINE_SITE_FILE_NAME);
      return props;
    }
    log.info("Beeline configuration file at: {}", fileLocation);
    // load the properties from config file
    Configuration conf = new Configuration(false);
    conf.addResource(new Path(new File(fileLocation).toURI()));
    try {
      for (Entry<String, String> kv : conf) {
        String key = kv.getKey();
        if (key.startsWith(BEELINE_CONNECTION_NAMED_JDBC_URL_PREFIX)) {
          // using conf.get(key) to help with variable substitution
          props.setProperty(key.substring(BEELINE_CONNECTION_NAMED_JDBC_URL_PREFIX.length()),
              conf.get(key));
        }
      }
    } catch (Exception e) {
      throw new BeelineSiteParseException(e.getMessage(), e);
    }
    return props;
  }

};