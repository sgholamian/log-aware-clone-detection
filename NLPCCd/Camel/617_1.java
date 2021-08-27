//,temp,sample_2090.java,2,10,temp,sample_2089.java,2,9
//,3
public class xxx {
public void testNodeToSourceThenToInputStream() throws Exception {
Document document = converter.convertTo(Document.class, "<?xml version=\"1.0\"?><hello>world!</hello>");
Element element = document.getDocumentElement();
Source source = converter.convertTo(Source.class, element);
assertNotNull("Could not convert from Node to Source!", source);


log.info("found source");
}

};