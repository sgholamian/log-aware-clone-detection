//,temp,sample_1201.java,2,7,temp,sample_1198.java,2,8
//,3
public class xxx {
public void testValidXML() throws Exception {
String payload = IOUtils.toString(ClassLoader.getSystemResourceAsStream("xml/article-1.xml"));
String result = getProcessor("sch/schematron-1.sch", null).validate(payload);


log.info("schematron report");
}

};