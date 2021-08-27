//,temp,BeelineSiteParser.java,77,102,temp,UserHS2ConnectionFileParser.java,70,96
//,3
public class xxx {
  @Override
  public Properties getConnectionProperties() throws BeelineHS2ConnectionFileParseException {
    Properties props = new Properties();
    String fileLocation = getFileLocation();
    if (fileLocation == null) {
      log.debug("User connection configuration file not found");
      return props;
    }
    log.info("Using connection configuration file at " + fileLocation);
    props.setProperty(HS2ConnectionFileParser.URL_PREFIX_PROPERTY_KEY, "jdbc:hive2://");
    // load the properties from config file
    Configuration conf = new Configuration(false);
    conf.addResource(new Path(new File(fileLocation).toURI()));
    try {
      for (Entry<String, String> kv : conf) {
        String key = kv.getKey();
        if (key.startsWith(BEELINE_CONNECTION_PROPERTY_PREFIX)) {
          props.setProperty(key.substring(BEELINE_CONNECTION_PROPERTY_PREFIX.length()),
              kv.getValue());
        }
      }
    } catch (Exception ex) {
      throw new BeelineHS2ConnectionFileParseException(ex.getMessage(), ex);
    }

    return props;
  }

};