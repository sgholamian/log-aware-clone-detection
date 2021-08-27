//,temp,sample_1200.java,2,8,temp,sample_1202.java,2,8
//,3
public class xxx {
public void testInValidXML() throws Exception {
String payload = IOUtils.toString(ClassLoader.getSystemResourceAsStream("xml/article-2.xml"));
String result = getProcessor("sch/schematron-2.sch", null).validate(payload);


log.info("schematron report");
}

};