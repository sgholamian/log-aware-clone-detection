//,temp,sample_6566.java,2,17,temp,sample_1582.java,2,17
//,3
public class xxx {
public void dummy_method(){
Assert.assertEquals("log:a", details.get(1).getEndpointUri());
Assert.assertEquals("32", details.get(1).getLineNumber());
Assert.assertEquals("netty4-http:http:someserver:80/hello", details.get(2).getEndpointUri());
Assert.assertEquals("36", details.get(2).getLineNumber());
List<ParserResult> list = CamelJavaParserHelper.parseCamelConsumerUris(method, true, true);
for (ParserResult result : list) {
}
Assert.assertEquals("timer:foo?period=4999", list.get(0).getElement());
list = CamelJavaParserHelper.parseCamelProducerUris(method, true, true);
for (ParserResult result : list) {


log.info("producer");
}
}

};