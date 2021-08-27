//,temp,sample_8473.java,2,8,temp,sample_5944.java,2,10
//,3
public class xxx {
public void testConvertToXmlObject() throws Exception {
Exchange exchange = createExchangeWithBody("<hello>world!</hello>");
Message in = exchange.getIn();
XmlObject object = in.getBody(XmlObject.class);
assertNotNull("Should have created an XmlObject!", object);


log.info("found");
}

};