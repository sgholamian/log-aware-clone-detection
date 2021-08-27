//,temp,sample_1200.java,2,8,temp,sample_1202.java,2,8
//,3
public class xxx {
public void testInvalidXMLWithClientResolver() throws Exception {
String payload = IOUtils.toString(ClassLoader.getSystemResourceAsStream("xml/article-3.xml"));
String result = getProcessor("sch/schematron-3.sch", new ClientUriResolver()).validate(payload);


log.info("schematron report");
}

};