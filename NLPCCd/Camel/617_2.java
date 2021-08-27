//,temp,sample_2090.java,2,10,temp,sample_2089.java,2,9
//,3
public class xxx {
public void testStreamSourceToDomSource() throws Exception {
StreamSource streamSource = new StreamSource(new StringReader("<hello>world!</hello>"));
DOMSource domSource = converter.convertTo(DOMSource.class, streamSource);
assertNotNull("Could not convert to a DOMSource!", domSource);


log.info("found document");
}

};