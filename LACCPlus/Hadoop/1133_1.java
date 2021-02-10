//,temp,JsonSerDeser.java,118,127,temp,JsonSerDeser.java,100,109
//,2
public class xxx {
  @SuppressWarnings("unchecked")
  public synchronized T fromFile(File jsonFile)
      throws IOException, JsonParseException, JsonMappingException {
    try {
      return mapper.readValue(jsonFile, classType);
    } catch (IOException e) {
      LOG.error("Exception while parsing json file {}: {}", jsonFile, e);
      throw e;
    }
  }

};