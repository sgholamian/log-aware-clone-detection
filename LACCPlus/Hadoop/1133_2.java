//,temp,JsonSerDeser.java,118,127,temp,JsonSerDeser.java,100,109
//,2
public class xxx {
  @SuppressWarnings("unchecked")
  public synchronized T fromJson(String json)
      throws IOException, JsonParseException, JsonMappingException {
    try {
      return mapper.readValue(json, classType);
    } catch (IOException e) {
      LOG.error("Exception while parsing json : " + e + "\n" + json, e);
      throw e;
    }
  }

};