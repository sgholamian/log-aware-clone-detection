//,temp,BaseAMRMProxyTest.java,242,259,temp,XmlCustomResourceTypeTestCase.java,88,94
//,3
public class xxx {
  private void logResponse(Document doc) {
    String responseStr = response.getEntity(String.class);
    LOG.info("Raw response from service URL {}: {}", path.toString(),
        responseStr);
    LOG.info("Parsed response from service URL {}: {}", path.toString(),
        toXml(doc));
  }

};