//,temp,sample_505.java,2,16,temp,sample_506.java,2,17
//,3
public class xxx {
protected void initialize() {
try {
DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
docFactory.setNamespaceAware(true);
DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
_docResponse = docBuilder.parse(new InputSource(new StringReader(_xmlResponse)));
if (_docResponse != null) {
parse(_docResponse.getDocumentElement());
}
} catch (ParserConfigurationException e) {


log.info("error parsing the response");
}
}

};